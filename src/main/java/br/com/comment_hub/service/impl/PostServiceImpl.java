package br.com.comment_hub.service.impl;

import br.com.comment_hub.dto.request.PostRequest;
import br.com.comment_hub.exception.NotFoundException;
import br.com.comment_hub.exception.UnauthorizedException;
import br.com.comment_hub.mapper.PostMapper;
import br.com.comment_hub.model.core.Author;
import br.com.comment_hub.model.core.Post;
import br.com.comment_hub.model.core.User;
import br.com.comment_hub.repository.AuthorRepository;
import br.com.comment_hub.repository.PostRepository;
import br.com.comment_hub.repository.UserRepository;
import br.com.comment_hub.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, AuthorRepository authorRepository,
                           UserRepository userRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }

    @Override
    public Map<String, Object> create(PostRequest postRequest, String authorEmail) {
        User user = userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        Author author = authorRepository.findByUser(user)
                .orElseGet(() -> {
                    Author newAuthor = Author.builder().user(user).build();

                    return authorRepository.save(newAuthor);
                });

        Post post = postMapper.toPost(postRequest);
        post.setAuthor(author);

        Post savedPost = postRepository.save(post);

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        data.put("post", postMapper.toPostResponse(savedPost));
        response.put("data", data);
        response.put("message", "Post criado com sucesso!");

        return response;
    }

    @Override
    public Map<String, Object> findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        data.put("post", postMapper.toPostResponse(post));
        response.put("data", data);
        response.put("message", "Post encontrado com sucesso!");

        return response;
    }

    @Override
    public Map<String, Object> findAll(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        data.put("posts", posts.map(postMapper::toPostResponse).getContent());
        data.put("totalElements", posts.getTotalElements());
        data.put("totalPages", posts.getTotalPages());
        data.put("currentPage", posts.getNumber() + 1);

        response.put("data", data);
        response.put("message", "Posts listados com sucesso!");

        return response;
    }

    @Override
    public Map<String, Object> update(Long id, PostRequest postRequest, String authorEmail) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post não encontrado"));

        userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (!post.getAuthor().getUser().getEmail().equals(authorEmail)) {
            throw new UnauthorizedException("Você não tem permissão para atualizar este post");
        }

        postMapper.updatePostFromRequest(post, postRequest);

        Post updatedPost = postRepository.save(post);

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        data.put("post", postMapper.toPostResponse(updatedPost));
        response.put("data", data);
        response.put("message", "Post atualizado com sucesso!");

        return response;
    }

    @Override
    public Map<String, Object> delete(Long id, String authorEmail) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post não encontrado"));

        userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (!post.getAuthor().getUser().getEmail().equals(authorEmail)) {
            throw new UnauthorizedException("Você não tem permissão para deletar este post");
        }

        postRepository.delete(post);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Post deletado com sucesso!");

        return response;
    }
}
