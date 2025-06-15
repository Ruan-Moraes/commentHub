package br.com.comment_hub;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Comment Hub API",
                version = "1.0.0",
                description = "API para gerenciamento de comentários em posts de blog."
        )
)
@SpringBootApplication
public class CommentHubApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommentHubApplication.class, args);
	}
}
