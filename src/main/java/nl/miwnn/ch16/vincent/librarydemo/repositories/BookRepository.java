package nl.miwnn.ch16.vincent.librarydemo.repositories;

import nl.miwnn.ch16.vincent.librarydemo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
