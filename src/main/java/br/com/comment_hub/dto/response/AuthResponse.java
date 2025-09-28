package br.com.comment_hub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder()
@Data()
@AllArgsConstructor()
@NoArgsConstructor()
public class AuthResponse {
    private String token;
    
    private String refreshToken;
    
    private UserResponse user;
}