package it.epicode.w6d4.Models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Transient
    private UUID autoreId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autore_id", nullable = false)
    @JsonIgnore
    private Autore autore;

    private String categoria;
    private String titolo;
    private String cover;
    private String contenuto;
    private int tempoDiLettura;

    public Post(UUID autoreId, Autore autore, String categoria, String titolo, String cover, String contenuto, int tempoDiLettura) {
        this.autoreId = autoreId;
        this.autore = autore;
        this.categoria = categoria;
        this.titolo = titolo;
        this.cover = cover;
        this.contenuto = contenuto;
        this.tempoDiLettura = tempoDiLettura;
    }
}
