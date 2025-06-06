package nl.miwnn.ch16.vincent.librarydemo.controller;

import nl.miwnn.ch16.vincent.librarydemo.model.Book;
import nl.miwnn.ch16.vincent.librarydemo.model.Copy;
import nl.miwnn.ch16.vincent.librarydemo.repositories.BookRepository;
import nl.miwnn.ch16.vincent.librarydemo.repositories.CopyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * @author Vincent Velthuizen
 * Handle all requests related to copies
 */

@Controller
@RequestMapping("/copy")
public class CopyController {
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;

    public CopyController(BookRepository bookRepository, CopyRepository copyRepository) {
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
    }

    @GetMapping("/new/{bookId}")
    private String createNewCopy(@PathVariable("bookId") Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isPresent()) {
            Copy copy = new Copy(optionalBook.get());
            copyRepository.save(copy);
        }

        return "redirect:/";
    }

}
