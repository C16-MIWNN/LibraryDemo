package nl.miwnn.ch16.vincent.librarydemo.controller;

import nl.miwnn.ch16.vincent.librarydemo.model.Book;
import nl.miwnn.ch16.vincent.librarydemo.repositories.AuthorRepository;
import nl.miwnn.ch16.vincent.librarydemo.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Vincent Velthuizen
 * Handle all requests related directly or primarily to books
 */

@Controller
public class BookController {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BookController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    private String setupBookOverview(Model datamodel, Book formBook, boolean formModalHidden) {
        datamodel.addAttribute("allBooks", bookRepository.findAll());
        datamodel.addAttribute("formBook", formBook);
        datamodel.addAttribute("allAuthors", authorRepository.findAll());
        datamodel.addAttribute("formModalHidden", formModalHidden);

        return "bookOverview";
    }

    private String setupBookDetail(Model datamodel, Book bookToShow, Book formBook, boolean formModalHidden) {
        datamodel.addAttribute("book", bookToShow);
        datamodel.addAttribute("formBook", formBook);
        datamodel.addAttribute("allAuthors", authorRepository.findAll());
        datamodel.addAttribute("formModalHidden", formModalHidden);

        return "bookDetails";
    }

    @GetMapping({"/", "/book/overview"})
    private String showBookOverview(Model datamodel) {
        return setupBookOverview(datamodel, new Book(), true);
    }

    @GetMapping("/book/detail/{title}")
    private String showBookDetailPage(@PathVariable("title") String title, Model datamodel) {
        Optional<Book> bookOptional = bookRepository.findByTitle(title);

        if (bookOptional.isEmpty()) {
            return "redirect:/book/overview";
        }

        return setupBookDetail(datamodel, bookOptional.get(), bookOptional.get(), true);
    }

    @GetMapping("/book/new")
    private String showNewBookForm(Model datamodel) {
        datamodel.addAttribute("formBook", new Book());
        datamodel.addAttribute("allAuthors", authorRepository.findAll());

        return "bookForm";
    }

    @PostMapping("/book/save")
    private String saveBook(@ModelAttribute("formBook") Book bookToBeSaved, BindingResult result, Model datamodel) {
        checkBookTitleExists(bookToBeSaved, result);

        if (result.hasErrors() && bookToBeSaved.getBookId() != null) {
            Book originalBookDetails = bookRepository.findById(bookToBeSaved.getBookId()).orElse(new Book());
            return setupBookDetail(datamodel, originalBookDetails, bookToBeSaved, false);
        } else if (result.hasErrors()) {
            return setupBookOverview(datamodel, bookToBeSaved, false);
        }

        bookRepository.save(bookToBeSaved);
        return "redirect:/book/detail/" + bookToBeSaved.getTitle();
    }

    private void checkBookTitleExists(Book bookToBeSaved, BindingResult result) {
        Optional<Book> sameName = bookRepository.findByTitle(bookToBeSaved.getTitle());
        if (sameName.isPresent() && !sameName.get().getBookId().equals(bookToBeSaved.getBookId())) {
            result.addError(new FieldError("book", "title", "this title is already in use"));
        }
    }

    @GetMapping("/book/delete/{bookTitle}")
    private String deleteBook(@PathVariable("bookTitle") String title) {
        bookRepository.findByTitle(title).ifPresent(bookRepository::delete);
        return "redirect:/book/overview";
    }
}
