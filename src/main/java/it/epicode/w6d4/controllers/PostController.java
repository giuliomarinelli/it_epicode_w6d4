package it.epicode.w6d4.controllers;

import com.cloudinary.Cloudinary;
import it.epicode.w6d4.Models.DTO.AutoreDTO;
import it.epicode.w6d4.Models.DTO.PostDTO;
import it.epicode.w6d4.Models.entities.Autore;
import it.epicode.w6d4.Models.entities.Post;
import it.epicode.w6d4.configuration.AppConfig;
import it.epicode.w6d4.exceptions.BadRequestException;
import it.epicode.w6d4.exceptions.NotFoundException;
import it.epicode.w6d4.exceptions.PaginationSearchException;
import it.epicode.w6d4.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class PostController {
    @Autowired
    private PostService postSvc;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/post")
    public Page<Post> findAll(Pageable pageable, @RequestParam(required = false) UUID autoreId) throws PaginationSearchException, BadRequestException {
        return postSvc.findAll(pageable, autoreId);
    }

    @GetMapping("/post/{id}")
    public Post findById(@PathVariable UUID id) throws NotFoundException, BadRequestException {
        return postSvc.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post")
    public Post create(@RequestBody @Validated PostDTO postDTO, BindingResult validation) throws BadRequestException {
        if (validation.hasErrors()) throw new BadRequestException(AppConfig.generateValidationErrorMessage(validation));
        return postSvc.create(postDTO);
    }

    @PutMapping("/post/{id}")
    public Post update(@PathVariable UUID id, @RequestBody @Validated PostDTO postDTO, BindingResult validation) throws BadRequestException {
        if (validation.hasErrors()) throw new BadRequestException(AppConfig.generateValidationErrorMessage(validation));
        return postSvc.update(id, postDTO);
    }

    @PatchMapping("/post/{id}/upload-cover")
    public Post upload(@PathVariable UUID id, @RequestParam("upload-cover") MultipartFile file) throws IOException, BadRequestException, NotFoundException {
        String url = (String) cloudinary.uploader().upload(file.getBytes(), new HashMap()).get("url");
        System.out.println(file.getName());
        return postSvc.uploadCover(id, url);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/post/{id}")
    public void delete(@PathVariable UUID id) throws BadRequestException {

        postSvc.delete(id);
    }
}
