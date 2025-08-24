package br.com.comment_hub.service;

import br.com.comment_hub.dto.request.PostRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface PostService {
    Map<String, Object> create(PostRequest postRequest, String authorEmail);

    Map<String, Object> findById(Long id);

    Map<String, Object> findAll(Pageable pageable);

    Map<String, Object> update(Long id, PostRequest postRequest, String authorEmail);

    Map<String, Object> delete(Long id, String authorEmail);
}
