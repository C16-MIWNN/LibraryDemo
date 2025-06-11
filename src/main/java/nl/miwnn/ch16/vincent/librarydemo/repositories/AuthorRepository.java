package nl.miwnn.ch16.vincent.librarydemo.repositories;

import nl.miwnn.ch16.vincent.librarydemo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findAuthorByName(String name);
}
