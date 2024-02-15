package it.epicode.w6d4.repositories;

import it.epicode.w6d4.Models.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PostRepository extends JpaRepository<Post, UUID>, PagingAndSortingRepository<Post, UUID> {
    @Query("SELECT p FROM Post p WHERE p.autore.id = :autoreId")
    public Page<Post> getPostsByAutore(Pageable pageable, UUID autoreId);

}
