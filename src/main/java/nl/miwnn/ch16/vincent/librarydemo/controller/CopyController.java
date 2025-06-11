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

    @GetMapping("/new/{bookTitle}")
    private String createNewCopy(@PathVariable("bookTitle") String bookTitle) {
        Optional<Book> optionalBook = bookRepository.findByTitle(bookTitle);

        if (optionalBook.isEmpty()) {
            return "redirect:/";
        }

        Copy copy = new Copy();
        copy.setBook(optionalBook.get());
        copyRepository.save(copy);

        return redirectToBookDetailPage(copy);
    }

    private static String redirectToBookDetailPage(Copy copy) {
        return String.format("redirect:/book/detail/%s", copy.getBook().getTitle());
    }

    @GetMapping("/borrow/{copyId}")
    private String makeCopyUnavailable(@PathVariable("copyId") Long copyId) {
        return setAvailabilityAndRedirectToBook(copyId, false);
    }

    @GetMapping("/return/{copyId}")
    private String makeCopyAvailable(@PathVariable("copyId") Long copyId) {
        return setAvailabilityAndRedirectToBook(copyId, true);
    }

    private String setAvailabilityAndRedirectToBook(Long copyId, boolean available) {
        Optional<Copy> optionalCopy = copyRepository.findById(copyId);

        if (optionalCopy.isEmpty()) {
            return "redirect:/book/overview";
        }

        Copy copy = optionalCopy.get();
        copy.setAvailable(available);
        copyRepository.save(copy);

        return redirectToBookDetailPage(copy);
    }
}
