package br.com.comment_hub.service.impl;

import br.com.comment_hub.model.core.User;
import br.com.comment_hub.service.TokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${commenthub.security.secret}")
    private String SECRET;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("name", user.getName())
                .withExpiresAt(java.util.Date.from(java.time.Instant.now().plusSeconds(3600))) // 60 minutos
                .sign(algorithm);
    }
}
