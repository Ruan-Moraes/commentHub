package br.com.comment_hub.service.impl;

import br.com.comment_hub.dto.request.CommentRequest;
import br.com.comment_hub.exception.NotFoundException;
import br.com.comment_hub.exception.UnauthorizedException;
import br.com.comment_hub.mapper.CommentMapper;
import br.com.comment_hub.model.core.Author;
import br.com.comment_hub.model.core.Comment;
import br.com.comment_hub.model.core.Post;
import br.com.comment_hub.model.core.User;
import br.com.comment_hub.repository.AuthorRepository;
import br.com.comment_hub.repository.CommentRepository;
import br.com.comment_hub.repository.PostRepository;
import br.com.comment_hub.repository.UserRepository;
import br.com.comment_hub.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,
                              AuthorRepository authorRepository, UserRepository userRepository,
                              CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public Map<String, Object> create(CommentRequest commentRequest, String authorEmail) {
        User user = userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new NotFoundException("Post não encontrado"));

        Author author = authorRepository.findByUser(user)
                .orElseGet(() -> {
                    Author newAuthor = Author.builder().user(user).build();
                    return authorRepository.save(newAuthor);
                });

        Comment comment = commentMapper.toComment(commentRequest);
        comment.setAuthor(author);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        data.put("comment", commentMapper.toCommentResponse(savedComment));
        response.put("data", data);
        response.put("message", "Comentário criado com sucesso!");

        return response;
    }

    @Override
    public Map<String, Object> findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comentário não encontrado"));

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        data.put("comment", commentMapper.toCommentResponse(comment));
        response.put("data", data);
        response.put("message", "Comentário encontrado com sucesso!");

        return response;
    }

    @Override
    public Map<String, Object> findAll(Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        data.put("comments", comments.map(commentMapper::toCommentResponse).getContent());
        data.put("totalElements", comments.getTotalElements());
        data.put("totalPages", comments.getTotalPages());
        data.put("currentPage", comments.getNumber() + 1);

        response.put("data", data);
        response.put("message", "Comentários listados com sucesso!");

        return response;
    }

    @Override
    public Map<String, Object> findByPostId(Long postId, Pageable pageable) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post não encontrado"));

        Page<Comment> comments = commentRepository.findByPostId(postId, pageable);

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        data.put("comments", comments.map(commentMapper::toCommentResponse).getContent());
        data.put("totalElements", comments.getTotalElements());
        data.put("totalPages", comments.getTotalPages());
        data.put("currentPage", comments.getNumber() + 1);
        data.put("postTitle", post.getTitle());

        response.put("data", data);
        response.put("message", "Comentários do post listados com sucesso!");

        return response;
    }

    @Override
    public Map<String, Object> update(Long id, CommentRequest commentRequest, String authorEmail) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comentário não encontrado"));

        userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (!comment.getAuthor().getUser().getEmail().equals(authorEmail)) {
            throw new UnauthorizedException("Você não tem permissão para atualizar este comentário");
        }

        commentMapper.updateCommentFromRequest(comment, commentRequest);

        Comment updatedComment = commentRepository.save(comment);

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        data.put("comment", commentMapper.toCommentResponse(updatedComment));
        response.put("data", data);
        response.put("message", "Comentário atualizado com sucesso!");

        return response;
    }

    @Override
    public Map<String, Object> delete(Long id, String authorEmail) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comentário não encontrado"));

        userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (!comment.getAuthor().getUser().getEmail().equals(authorEmail)) {
            throw new UnauthorizedException("Você não tem permissão para deletar este comentário");
        }

        commentRepository.delete(comment);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Comentário deletado com sucesso!");

        return response;
    }
}