package it.epicode.w6d4.services;

import it.epicode.w6d4.Models.DTO.AutoreDTO;
import it.epicode.w6d4.Models.entities.Autore;
import it.epicode.w6d4.exceptions.BadRequestException;
import it.epicode.w6d4.exceptions.ConstraintReferenceError;
import it.epicode.w6d4.exceptions.NotFoundException;
import it.epicode.w6d4.exceptions.PaginationSearchException;
import it.epicode.w6d4.repositories.AutoreRepository;
import jakarta.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class AutoreService {
    @Autowired
    AutoreRepository autoreRp;

    @Autowired
    JavaMailSenderImpl javaMailSender;

    public Page<Autore> findAll(Pageable pageable) throws PaginationSearchException {
        try {
            return autoreRp.findAll(pageable);
        } catch (Exception e) {
            throw new PaginationSearchException("Error while searching authors");
        }
    }

    public Autore findById(UUID id) throws NotFoundException {
        return autoreRp.findById(id).orElseThrow(() -> new NotFoundException("Author with id = " + id + " not found"));
    }

    private void sendWelcomeEmail(String to, String nome) throws UnsupportedEncodingException {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setFrom("Admin <giuliomarinelli25@gmail.com>");
        msg.setSubject("Welcome to our service");
        msg.setText("Hello " + nome + ", We are proud to tell you that a new adventure begun since you subscribed to our API.\n\n Have a nice day!\n\n Admin");
        javaMailSender.send(msg);
    }

    public Autore create(AutoreDTO autoreDTO) throws ConstraintReferenceError, UnsupportedEncodingException {
        Autore a = new Autore(autoreDTO.nome(), autoreDTO.cognome(), autoreDTO.email(),
                LocalDate.parse(autoreDTO.dataDiNascita()));
        try {
            sendWelcomeEmail(a.getEmail(), a.getNome());
            return autoreRp.save(a);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("un valore chiave duplicato viola il vincolo univoco"))
                throw new ConstraintReferenceError("Email already exists. Cannot create");
            throw new ConstraintReferenceError("The operation violates data integrity");
        }
    }

    public Autore update(UUID id, AutoreDTO autoreDTO) throws BadRequestException {
        Autore autoreDaAggiornare = autoreRp.findById(id).orElseThrow(
                () -> new BadRequestException("Author you're trying to update doesn't exist (provided id = " + id + ")"));
        autoreDaAggiornare.setNome(autoreDTO.nome());
        autoreDaAggiornare.setCognome(autoreDTO.cognome());
        autoreDaAggiornare.generateAvatar();
        autoreDaAggiornare.setEmail(autoreDTO.email());
        autoreDaAggiornare.setDataDiNascita(LocalDate.parse(autoreDTO.dataDiNascita()));
        try {
            return autoreRp.save(autoreDaAggiornare);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("un valore chiave duplicato viola il vincolo univoco"))
                throw new ConstraintReferenceError("Email already exists. Cannot update");
            throw new ConstraintReferenceError("The operation violates data integrity");
        }
    }

    public void delete(UUID id) throws BadRequestException, DataIntegrityViolationException {
        Autore a = autoreRp.findById(id).orElseThrow(
                () -> new BadRequestException("Author you're trying to delete doesn't exist (provided id = " + id + ")"));
        try {
            autoreRp.delete(a);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("è ancora referenziata dalla tabella"))
                throw new ConstraintReferenceError("Cannot delete author because it is referenced by one ore more posts. You have to delete all referenced posts before deleting author");
            throw new ConstraintReferenceError("Error in data integrity");
        }

    }
}
