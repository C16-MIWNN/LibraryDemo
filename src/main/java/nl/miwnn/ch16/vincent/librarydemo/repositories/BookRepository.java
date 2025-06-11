package nl.miwnn.ch16.vincent.librarydemo.repositories;

import nl.miwnn.ch16.vincent.librarydemo.model.Author;
import nl.miwnn.ch16.vincent.librarydemo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
}
