package nl.miwnn.ch16.vincent.librarydemo.controller;

import nl.miwnn.ch16.vincent.librarydemo.model.Author;
import nl.miwnn.ch16.vincent.librarydemo.model.Book;
import nl.miwnn.ch16.vincent.librarydemo.model.Copy;
import nl.miwnn.ch16.vincent.librarydemo.repositories.AuthorRepository;
import nl.miwnn.ch16.vincent.librarydemo.repositories.BookRepository;
import nl.miwnn.ch16.vincent.librarydemo.repositories.CopyRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vincent Velthuizen
 * Set some intitial data in the database for (manual) testing purposes.
 */

@Controller
public class InitializeController {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;
    
    public InitializeController(AuthorRepository authorRepository, BookRepository bookRepository, CopyRepository copyRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
    }

    @EventListener
    private void seed(ContextRefreshedEvent ignoredEvent) {
        if (authorRepository.count() == 0) {
            intializeDB();
        }
    }

    private void intializeDB() {
        Author brandon = makeAuthor("Brandon Sanderson");
        Author tolkien = makeAuthor("J.R.R. Tolkien");
        Author patrick = makeAuthor("Patrick Rothfuss");

        Book hobbit = makeBook("The Hobbit", tolkien);
        makeCopies(hobbit, 3);

        Book lotr = makeBook("The Lord of the Rings", tolkien);
        makeCopies(lotr, 3);

        Book finalEmpire = makeBook("The Final Empire", brandon);
        makeCopies(finalEmpire, 1);

        Book notw = makeBook("The Name of the Wind", patrick);
        makeCopies(notw, 2);
    }

    private Author makeAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        authorRepository.save(author);
        return author;
    }

    private Book makeBook(String title, Author ... authors) {
        Book book = new Book();

        book.setTitle(title);

        Set<Author> authorSet = new HashSet<>(Arrays.asList(authors));
        book.setAuthors(authorSet);

        bookRepository.save(book);
        return book;
    }

    private Copy makeCopy(Book book, boolean available) {
        Copy copy = new Copy();

        copy.setBook(book);
        copy.setAvailable(available);

        copyRepository.save(copy);
        return copy;
    }

    private void makeCopies(Book book, int numberOfCopies) {
        for (int i = 0; i < numberOfCopies; i++) {
            makeCopy(book, i % 2 == 0); // uneven numbered copies are not available
        }
    }
}
