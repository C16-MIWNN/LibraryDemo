package nl.miwnn.ch16.vincent.librarydemo.controller;

import nl.miwnn.ch16.vincent.librarydemo.model.Author;
import nl.miwnn.ch16.vincent.librarydemo.repositories.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        datamodel.addAttribute("formAuthor", new Author());

        return "authorOverview";
    }

    @PostMapping("/save")
    private String saveOrUpdateAuthor(@ModelAttribute("formAuthor") Author authorToBeSaved, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            authorRepository.save(authorToBeSaved);
        }

        return "redirect:/author/overview";
    }
}
