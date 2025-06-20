package br.com.comment_hub.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity()
@Table(name = "TB_AUTHOR")
@NoArgsConstructor()
@AllArgsConstructor()
@Data()
@Builder()
public class Author {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Post> posts;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Comment> comments;

    @OneToOne()
    private User user;
}
