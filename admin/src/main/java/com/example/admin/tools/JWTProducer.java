package com.example.admin.tools;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTProducer {
    private static final String admin_secret_key="dadhiuAqidqa";//Admin秘钥
    private static Algorithm adminAlgorithm = Algorithm.HMAC256(admin_secret_key);//加密算法

    private static JWTVerifier adminVerifier=JWT.require(adminAlgorithm)
            .withClaimPresence("account")
            .build();//校验器
    public static String getToken(String account){
        String token=null;
        try {
            token = JWT.create()
                    .withClaim("account",account)
                    .sign(adminAlgorithm);
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
        }finally {
            return token;
        }
    }
}
