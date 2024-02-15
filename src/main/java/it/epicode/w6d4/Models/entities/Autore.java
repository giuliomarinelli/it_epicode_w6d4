package it.epicode.w6d4.Models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Data
@Entity
@NoArgsConstructor
public class Autore {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    private String cognome;
    @Column(unique = true)
    private String email;
    private LocalDate dataDiNascita;
    private String avatar;

    @OneToMany(mappedBy = "autore")
    @JsonIgnore
    private List<Post> posts;


    public Autore(String nome, String cognome, String email, LocalDate dataDiNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.dataDiNascita = dataDiNascita;
        generateAvatar();
    }

    public void generateAvatar() {
        avatar = "https://ui-avatars.com/api/?name=" + nome + "+" + cognome;
    }
}
