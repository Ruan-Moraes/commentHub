package br.com.comment_hub.service;

import br.com.comment_hub.model.core.User;

public interface TokenService {
    String generateToken(User user);

    Boolean validate(String token);

    String getSubject(String token);
}
