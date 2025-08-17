package br.com.comment_hub.repository;

import br.com.comment_hub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u.name FROM User u WHERE u.email = :email")
    String selectNameByEmail(String email);
}
