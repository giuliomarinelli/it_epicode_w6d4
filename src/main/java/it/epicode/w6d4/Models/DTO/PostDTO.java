package it.epicode.w6d4.Models.DTO;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record PostDTO(
        @NotNull(message = "autoreId is required")
//        @Pattern(regexp = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$",
//        message = "provided autoreId is not a valid Universal Unique ID")
        UUID autoreId,

        @NotNull(message = "categoria is required")
        @NotBlank(message = "categoria must not be empty")
        @Size(message = "categoria's length can't be more than 20 characters")
        String categoria,

        @NotNull(message = "titolo is required")
        @NotBlank(message = "titolo must not be empty")
        @Size(message = "titolo's length can't be more than 50 characters")
        String titolo,
        @NotNull(message = "contenuto is required")
        @NotBlank(message = "contenuto must not be empty")
        String contenuto,
        @NotNull(message = "tempoDiLettura is required")
        @Min(value = 0, message = "tempoDiLettura can't be a negative number")
        Integer tempoDiLettura
) {
}
