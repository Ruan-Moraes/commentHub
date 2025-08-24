package br.com.comment_hub.repository;

import br.com.comment_hub.model.core.Author;
import br.com.comment_hub.model.core.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByUser(User user);
}
