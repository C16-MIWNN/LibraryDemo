package nl.miwnn.ch16.vincent.librarydemo.controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import nl.miwnn.ch16.vincent.librarydemo.model.Author;
import nl.miwnn.ch16.vincent.librarydemo.model.Book;
import nl.miwnn.ch16.vincent.librarydemo.model.Copy;
import nl.miwnn.ch16.vincent.librarydemo.repositories.AuthorRepository;
import nl.miwnn.ch16.vincent.librarydemo.repositories.BookRepository;
import nl.miwnn.ch16.vincent.librarydemo.repositories.CopyRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Controller
public class InitializeController {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;
    private final Map<String, Author> authorCache = new HashMap<>();

    public InitializeController(AuthorRepository authorRepository, BookRepository bookRepository, CopyRepository copyRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
    }

    @EventListener
    private void seed(ContextRefreshedEvent ignoredEvent) {
        if (authorRepository.count() == 0) {
            initializeDB();
        }
    }

    private void initializeDB() {
        try {
            loadAuthors();
            loadBooks();
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Failed to initialize database from CSV files", e);
        }
    }

    private void loadAuthors() throws IOException, CsvValidationException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(
                new ClassPathResource("/example_data/authors.csv").getInputStream()))) {
            
            // Skip header
            reader.skip(1);

            for (String[] authorLine : reader) {
                Author author = new Author();

                author.setName(authorLine[0]);
                author.setImageUrl(authorLine[1]);

                authorRepository.save(author);
                authorCache.put(author.getName(), author);
            }
        }
    }

    private void loadBooks() throws IOException, CsvValidationException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(
                new ClassPathResource("/example_data/books.csv").getInputStream()))) {
            
            // Skip header
            reader.skip(1);

            for (String[] bookLine : reader) {
                Book book = new Book();
                book.setTitle(bookLine[0]);
                book.setDescription(bookLine[1]);
                book.setImageUrl(bookLine[2]);
                
                Set<Author> authors = new HashSet<>();
                String[] authorNames = bookLine[3].split(",");
                for (String authorName : authorNames) {
                    authors.add(authorCache.get(authorName.trim()));
                }
                book.setAuthors(authors);
                
                bookRepository.save(book);

                // Create copies
                createCopies(bookLine, book);
            }
        }
    }

    private void createCopies(String[] bookLine, Book book) {
        int totalCopies = Integer.parseInt(bookLine[4]);
        int unavailableCopies = Integer.parseInt(bookLine[5]);

        for (int i = 0; i < totalCopies; i++) {
            Copy copy = new Copy();
            copy.setBook(book);
            copy.setAvailable(unavailableCopies-- <= 0);
            copyRepository.save(copy);
        }
    }
}