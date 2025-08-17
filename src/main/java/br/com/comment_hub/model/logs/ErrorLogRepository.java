package br.com.comment_hub.model.logs;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity()
@Table(name = "tb_error_logs")
@NoArgsConstructor()
@AllArgsConstructor()
@Data()
@Builder()
public class ErrorLogRepository {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "stacktrace", columnDefinition = "TEXT")
    private String stacktrace;

    @Column(name = "status", length = 3)
    private String status;
}
