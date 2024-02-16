package it.epicode.w6d4.controllers;

import it.epicode.w6d4.Models.DTO.AutoreDTO;
import it.epicode.w6d4.Models.entities.Autore;
import it.epicode.w6d4.configuration.AppConfig;
import it.epicode.w6d4.exceptions.BadRequestException;
import it.epicode.w6d4.exceptions.NotFoundException;
import it.epicode.w6d4.exceptions.PaginationSearchException;
import it.epicode.w6d4.services.AutoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class AutoreController {

    @Autowired
    private AutoreService autoreSvc;


    @GetMapping("/autori")
    public Page<Autore> findAll(Pageable pageable) throws PaginationSearchException, BadRequestException {
        return autoreSvc.findAll(pageable);
    }

    @GetMapping("/autori/{id}")
    public Autore findById(@PathVariable UUID id) throws NotFoundException, BadRequestException {
        return autoreSvc.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/autori")
    public Autore create(@RequestBody @Validated AutoreDTO autoreDTO, BindingResult validation) throws BadRequestException, UnsupportedEncodingException {
        if (validation.hasErrors()) throw new BadRequestException(AppConfig.generateValidationErrorMessage(validation));
        return autoreSvc.create(autoreDTO);
    }

    @PutMapping("/autori/{id}")
    public Autore update(@PathVariable UUID id, @RequestBody @Validated AutoreDTO autoreDTO, BindingResult validation) throws BadRequestException {
        if (validation.hasErrors()) throw new BadRequestException(AppConfig.generateValidationErrorMessage(validation));
        return autoreSvc.update(id, autoreDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/autori/{id}")
    public void delete(@PathVariable @Validated UUID id) throws BadRequestException {
        autoreSvc.delete(id);
    }
}
