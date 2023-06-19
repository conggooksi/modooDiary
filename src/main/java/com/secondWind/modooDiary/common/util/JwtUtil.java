package com.secondWind.modooDiary.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {
    private static final String TOKEN_PREFIX = "Bearer";

    public static DecodedJWT decodeAuthorization(String authorization) {
        try {
            if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
                String authToken = authorization.substring(TOKEN_PREFIX.length());
                if (authToken != null) {
                    return JWT.decode(authToken);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getClaimValueFromAuthorization(String authorization, String key, Class<T> type) {
        DecodedJWT decodedJWT = decodeAuthorization(authorization);
        Claim claim = decodedJWT.getClaim(key);
        return claim.as(type);
    }
}
