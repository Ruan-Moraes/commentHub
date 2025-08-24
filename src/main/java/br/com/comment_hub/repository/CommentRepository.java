package br.com.comment_hub.repository;

import br.com.comment_hub.model.core.Author;
import br.com.comment_hub.model.core.Comment;
import br.com.comment_hub.model.core.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPost(Post post, Pageable pageable);
    Page<Comment> findByPostId(Long postId, Pageable pageable);
    Page<Comment> findByAuthor(Author author, Pageable pageable);
    Page<Comment> findByAuthorId(Long authorId, Pageable pageable);
}
