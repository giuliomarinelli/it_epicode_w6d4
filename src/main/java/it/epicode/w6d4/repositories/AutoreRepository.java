package it.epicode.w6d4.repositories;

import it.epicode.w6d4.Models.entities.Autore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AutoreRepository extends JpaRepository<Autore, UUID>, PagingAndSortingRepository<Autore, UUID> {

}
