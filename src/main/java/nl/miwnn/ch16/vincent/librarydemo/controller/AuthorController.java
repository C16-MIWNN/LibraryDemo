package nl.miwnn.ch16.vincent.librarydemo.controller;

import nl.miwnn.ch16.vincent.librarydemo.model.Author;
import nl.miwnn.ch16.vincent.librarydemo.repositories.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Vincent Velthuizen
 * Handle all requests related primarily to authors
 */

@Controller
@RequestMapping("/author")
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/overview")
    private String showAuthorOverview(Model datamodel) {
        datamodel.addAttribute("allAuthors", authorRepository.findAll());

        return "authorOverview";
    }

    @PostMapping("/save")
    private String saveOrUpdateAuthor(@ModelAttribute("formAuthor") Author author, BindingResult result, Model datamodel) {
        checkAuthorNameIsUnique(author, result);

        if (result.hasErrors()) {
            datamodel.addAttribute("authorToBeShown", author);
            datamodel.addAttribute("formAuthor", author);
            datamodel.addAttribute("formModalHidden", false);

            return "authorDetail";
        }

        authorRepository.save(author);

        datamodel.addAttribute("authorToBeShown", author);
        datamodel.addAttribute("formAuthor", author);
        datamodel.addAttribute("formModalHidden", true);

        return "authorDetail";
    }

    @GetMapping("/detail/{authorName}")
    private String showAuthorDetailPage(@PathVariable("authorName") String authorName, Model datamodel) {
        Optional<Author> author = authorRepository.findAuthorByName(authorName);

        if (author.isEmpty()) {
            return "redirect:/author";
        }

        datamodel.addAttribute("authorToBeShown", author.get());
        datamodel.addAttribute("formAuthor", author.get());
        datamodel.addAttribute("formModalHidden", true);

        return "authorDetail";
    }

    @GetMapping("/delete/{authorName}")
    private String deleteAuthor(@PathVariable("authorName") String name) {
        authorRepository.findAuthorByName(name).ifPresent(authorRepository::delete);

        return "redirect:/author/overview";
    }

    private void checkAuthorNameIsUnique(Author author, BindingResult result) {
        Optional<Author> sameName = authorRepository.findAuthorByName(author.getName());
        if (sameName.isPresent() && !sameName.get().getAuthorId().equals(author.getAuthorId())) {
            result.addError(new FieldError("formAuthor",
                    "name",
                    "there is already an author with this name"));
        }
    }
}
