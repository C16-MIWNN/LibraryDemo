package nl.miwnn.ch16.vincent.librarydemo.controller;

import nl.miwnn.ch16.vincent.librarydemo.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

/**
 * @author Vincent Velthuizen
 * Handle all requests related directly or primarily to books
 */

@Controller
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/")
    private String showBookOverview(Model datamodel) {
        datamodel.addAttribute("allBooks", bookRepository.findAll());

        return "bookOverview";
    }

}
