package br.com.comment_hub.service.impl;

import br.com.comment_hub.exception.TokenExpection;
import br.com.comment_hub.model.core.User;
import br.com.comment_hub.service.TokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${commenthub.security.secret}")
    private String SECRET;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);

            return JWT.create().withSubject(user.getEmail()).withClaim("name", user.getName()).withExpiresAt(java.util.Date.from(java.time.Instant.now().plusSeconds(3600))) // 60 minutos
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            return null;
        }
    }

    @Override
    public Boolean validate(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);

            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);

            return true;
        } catch (JWTVerificationException e) {
            throw new TokenExpection();
        }
    }

    @Override
    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);

            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            throw new TokenExpection();
        }
    }
}
