package com.example.gateway.tools;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTProducer {
    private static final String secret_key="dhoiadjaoimq";//秘钥
    private static final String admin_secret_key="dadhiuAqidqa";//Admin秘钥
    private static Algorithm algorithm = Algorithm.HMAC256(secret_key);//加密算法
    private static Algorithm adminAlgorithm = Algorithm.HMAC256(admin_secret_key);//加密算法
    private static JWTVerifier verifier=JWT.require(algorithm)
                    .withClaimPresence("uuid")
                    .build();//校验器
    private static JWTVerifier adminVerifier=JWT.require(adminAlgorithm)
            .withClaimPresence("account")
            .build();//校验器
    public static String getToken(String uuid){
        String token=null;
        try {
            token = JWT.create()
                    .withClaim("uuid",uuid)
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
        }finally {
            return token;
        }
    }
    public static DecodedJWT verify(String token){
        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            exception.printStackTrace();
            return null;
        }
    }
    public static DecodedJWT adiminVerify(String token){
        try {
            DecodedJWT jwt = adminVerifier.verify(token);
            return jwt;
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            exception.printStackTrace();
            return null;
        }
    }
}
