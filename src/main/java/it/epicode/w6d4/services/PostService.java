package it.epicode.w6d4.services;

import it.epicode.w6d4.Models.DTO.PostDTO;
import it.epicode.w6d4.Models.entities.Autore;
import it.epicode.w6d4.Models.entities.Post;
import it.epicode.w6d4.exceptions.BadRequestException;
import it.epicode.w6d4.exceptions.ConstraintReferenceError;
import it.epicode.w6d4.exceptions.NotFoundException;
import it.epicode.w6d4.exceptions.PaginationSearchException;
import it.epicode.w6d4.repositories.AutoreRepository;
import it.epicode.w6d4.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostService {
    @Autowired
    private PostRepository postRp;

    @Autowired
    private AutoreRepository autoreRp;

    public Page<Post> findAll(Pageable pageable, UUID autoreId) throws PaginationSearchException {
        try {
            if (autoreId == null || autoreId.toString().isEmpty())
                return postRp.findAll(pageable).map(p -> {
                    p.setAutoreId(p.getAutore().getId());
                    return p;
                });
            return postRp.getPostsByAutore(pageable, autoreId).map(p -> {
                p.setAutoreId(p.getAutore().getId());
                return p;
            });
        } catch (Exception e) {
            throw new PaginationSearchException();
        }
    }

    public Post findById(UUID id) throws NotFoundException {
        Post post = postRp.findById(id).orElseThrow(() -> new NotFoundException("Post with id = " + id + " not found"));
        post.setAutoreId(post.getAutore().getId());
        return post;
    }

    public Post create(PostDTO postDTO) throws ConstraintReferenceError {
        Autore a = autoreRp.findById(postDTO.autoreId())
                .orElseThrow(() -> new ConstraintReferenceError("userId "
                        + postDTO.autoreId() + " doesn't refer to any author"));
        Post p = new Post(postDTO.autoreId(), a, postDTO.categoria(), postDTO.titolo(),
                null, postDTO.contenuto(), postDTO.tempoDiLettura());
        return postRp.save(p);

    }

    public Post uploadCover(UUID id, String url) throws BadRequestException, NotFoundException {
        Post p = postRp.findById(id).orElseThrow(() -> new BadRequestException("Id " + id + " refers to an inexistent post"));
        Autore a = autoreRp.findById(p.getAutore().getId()).orElseThrow(
                () -> new NotFoundException("Author not found")
        );
        p.setAutoreId(a.getId());
        p.setCover(url);
        return postRp.save(p);
    }

    public Post update(UUID id, PostDTO postDTO) throws BadRequestException {
        Autore a = autoreRp.findById(postDTO.autoreId())
                .orElseThrow(() -> new ConstraintReferenceError("userId "
                        + postDTO.autoreId() + " doesn't refer to any author"));
        Post p = postRp.findById(id).orElseThrow(() -> new BadRequestException("Post with id = " + id + " doesn't exist"));
        p.setAutore(a);
        p.setCategoria(postDTO.categoria());
        p.setContenuto(postDTO.contenuto());
        p.setTitolo(postDTO.titolo());
        p.setTempoDiLettura(postDTO.tempoDiLettura());
        return postRp.save(p);
    }

    public void delete(UUID id) throws BadRequestException {
        Post post = postRp.findById(id).orElseThrow(() -> new BadRequestException("The post you asked to delete doesn't exist"));
        postRp.delete(post);
    }
}
