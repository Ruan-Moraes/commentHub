package br.com.comment_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogRepository extends JpaRepository<br.com.comment_hub.model.logs.ErrorLogRepository, Long> {

}
