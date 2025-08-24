package br.com.comment_hub.repository;

import br.com.comment_hub.model.core.Author;
import br.com.comment_hub.model.core.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByAuthor(Author author, Pageable pageable);
    Page<Post> findByAuthorId(Long authorId, Pageable pageable);
}
