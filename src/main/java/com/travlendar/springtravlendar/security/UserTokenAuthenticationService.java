package com.travlendar.springtravlendar.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;

class UserTokenAuthenticationService {
    static final long EXPIRATION_TIME = 259_200_000; // 3 days
    static final String Secret = "SecretKey";
    static final String TokenPrefix = "Bearer";
    static final String HeaderString = "Authorization";

    static void addAuthentication(HttpServletResponse response, String username) {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, Secret)
                .compact();
        response.addHeader(HeaderString, TokenPrefix + " " + JWT);
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HeaderString);
        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey(Secret)
                    .parseClaimsJws(token.replace(TokenPrefix, ""))
                    .getBody()
                    .getSubject();

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) :
                    null;
        }
        return null;
    }
}
