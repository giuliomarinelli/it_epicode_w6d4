package it.epicode.w6d4.Models.DTO;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AutoreDTO(
        @NotNull(message = "name is required")
        @NotBlank(message = "name must not be empty")
        @Size(min = 2, max = 30, message = "name's length must be at least 2 characters and not over 30 characters")
        String nome,
        @NotNull(message = "surname is required")
        @NotBlank(message = "surname must not be empty")
        @Size(min = 2, max = 30, message = "surname's length must be at least 2 characters and not over 30 characters")
        String cognome,
        @NotBlank(message = "email is required")
        @Email(message = "email not valid")
        String email,
        @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$",
        message = "date not valid. Required exact pattern: yyyy-mm-dd")
        String dataDiNascita
) {}
