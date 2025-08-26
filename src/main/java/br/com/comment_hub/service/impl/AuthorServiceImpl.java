package br.com.comment_hub.service.impl;

import br.com.comment_hub.exception.NotFoundException;
import br.com.comment_hub.mapper.AuthorMapper;
import br.com.comment_hub.mapper.CommentMapper;
import br.com.comment_hub.mapper.PostMapper;
import br.com.comment_hub.model.core.Author;
import br.com.comment_hub.model.core.Comment;
import br.com.comment_hub.model.core.Post;
import br.com.comment_hub.model.core.User;
import br.com.comment_hub.repository.AuthorRepository;
import br.com.comment_hub.repository.CommentRepository;
import br.com.comment_hub.repository.PostRepository;
import br.com.comment_hub.repository.UserRepository;
import br.com.comment_hub.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthorMapper authorMapper;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, PostRepository postRepository,
                             CommentRepository commentRepository, UserRepository userRepository,
                             AuthorMapper authorMapper, PostMapper postMapper, CommentMapper commentMapper) {
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.authorMapper = authorMapper;
        this.postMapper = postMapper;
        this.commentMapper = commentMapper;
    }

    @Override
    public Map<String, Object> findById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Autor não encontrado"));

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        data.put("author", authorMapper.toAuthorResponse(author));
        response.put("data", data);
        response.put("message", "Autor encontrado com sucesso!");

        return response;
    }

    @Override
    public Map<String, Object> findAll(Pageable pageable) {
        Page<Author> authors = authorRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        data.put("authors", authors.map(authorMapper::toAuthorResponse).getContent());
        data.put("totalElements", authors.getTotalElements());
        data.put("totalPages", authors.getTotalPages());
        data.put("currentPage", authors.getNumber() + 1);

        response.put("data", data);
        response.put("message", "Autores listados com sucesso!");

        return response;
    }

    @Override
    public Map<String, Object> findPostsByAuthorId(Long authorId, Pageable pageable) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Autor não encontrado"));

        Page<Post> posts = postRepository.findByAuthorId(authorId, pageable);

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        data.put("posts", posts.map(postMapper::toPostResponse).getContent());
        data.put("totalElements", posts.getTotalElements());
        data.put("totalPages", posts.getTotalPages());
        data.put("currentPage", posts.getNumber() + 1);
        data.put("author", authorMapper.toAuthorResponse(author));

        response.put("data", data);
        response.put("message", "Posts do autor listados com sucesso!");

        return response;
    }

    @Override
    public Map<String, Object> findCommentsByAuthorId(Long authorId, Pageable pageable) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Autor não encontrado"));

        Page<Comment> comments = commentRepository.findByAuthorId(authorId, pageable);

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        data.put("comments", comments.map(commentMapper::toCommentResponse).getContent());
        data.put("totalElements", comments.getTotalElements());
        data.put("totalPages", comments.getTotalPages());
        data.put("currentPage", comments.getNumber() + 1);
        data.put("author", authorMapper.toAuthorResponse(author));

        response.put("data", data);
        response.put("message", "Comentários do autor listados com sucesso!");

        return response;
    }

    @Override
    public Map<String, Object> getProfile(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        Author author = authorRepository.findByUser(user)
                .orElseGet(() -> {
                    Author newAuthor = Author.builder().user(user).build();

                    return authorRepository.save(newAuthor);
                });

        Page<Post> posts = postRepository.findByAuthor(author, Pageable.unpaged());
        Page<Comment> comments = commentRepository.findByAuthor(author, Pageable.unpaged());

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        data.put("author", authorMapper.toAuthorResponse(author));
        data.put("totalPosts", posts.getTotalElements());
        data.put("totalComments", comments.getTotalElements());

        response.put("data", data);
        response.put("message", "Perfil do autor obtido com sucesso!");

        return response;
    }
}