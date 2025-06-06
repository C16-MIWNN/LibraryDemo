package nl.miwnn.ch16.vincent.librarydemo.controller;

import nl.miwnn.ch16.vincent.librarydemo.model.Book;
import nl.miwnn.ch16.vincent.librarydemo.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping({"/", "/book/overview"})
    private String showBookOverview(Model datamodel) {
        datamodel.addAttribute("allBooks", bookRepository.findAll());

        return "bookOverview";
    }

    @GetMapping("/book/new")
    private String showNewBookForm(Model datamodel) {
        datamodel.addAttribute("formBook", new Book());

        return "bookForm";
    }

    @PostMapping("/book/save")
    private String saveOrUpdateBook(@ModelAttribute("formBook") Book bookToBeSaved, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
        } else {
            bookRepository.save(bookToBeSaved);
        }

        return "redirect:/book/overview";
    }
}
