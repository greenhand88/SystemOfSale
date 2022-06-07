package com.example.gateway.filters;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.gateway.tools.JWTProducer;
import com.example.gateway.utils.UserInfor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;


@Component
public class AuthLoginGlobalFilter implements GlobalFilter, Ordered {
    //配置文件中可以配置
    @Value("#{'${jwt.ignoreUrls}'.split(',')}")
    List<String> ignoreUrls;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${mq.config.fresh.exchange}")
    private String mqexchange;

    @Value("${mq.config.fresh.routeKey}")
    private String freshRouteKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUrl = exchange.getRequest().getPath().toString();
        //boolean status = CollectionUtil.contains(ignoreUrl, requestUrl);
        boolean status=true;
        synchronized(requestUrl){
            for(String url:ignoreUrls){
                if(requestUrl.contains(url)) {
                    status = false;
                    break;
                }
            }
        }
        if (!status){
            String token = exchange.getRequest().getHeaders().getFirst("token");
            ServerHttpResponse response = exchange.getResponse();
            //没有数据
            if (token==null) {
                JSONObject message = new JSONObject();
                message.put("message", "鉴权失败,请先登录");
                byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bits);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-Type", "text/json;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
                //有数据
            }else {
                //校验token
                DecodedJWT verify = JWTProducer.verify(token);
                Claim uuid = verify.getClaim("uuid");
                UserInfor userInfor = (UserInfor)redisTemplate.opsForValue().get(uuid.asString());
                if (userInfor==null){
                    JSONObject message = new JSONObject();
                    message.put("message", "token错误");
                    byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
                    DataBuffer buffer = response.bufferFactory().wrap(bits);
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    response.getHeaders().add("Content-Type", "text/json;charset=UTF-8");
                    return response.writeWith(Mono.just(buffer));
                }
                //将数据返回给下级服务器
                Consumer<HttpHeaders> httpHeaders = httpHeader -> {
                    httpHeader.set("uuid", uuid.asString());
                };
                //将现在的request，添加当前身份
                ServerHttpRequest mutableReq = exchange.getRequest().mutate().headers(httpHeaders).build();
                ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
                //刷新令牌持续时间
                amqpTemplate.convertAndSend(mqexchange,freshRouteKey,uuid.asString());
                return chain.filter(mutableExchange);
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
