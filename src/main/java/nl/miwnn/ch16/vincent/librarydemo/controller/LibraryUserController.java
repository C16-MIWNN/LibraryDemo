package nl.miwnn.ch16.vincent.librarydemo.controller;

import nl.miwnn.ch16.vincent.librarydemo.dto.NewLibraryUserDTO;
import nl.miwnn.ch16.vincent.librarydemo.service.LibraryUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Vincent Velthuizen
 * Purpose for the class
 */

@Controller
@RequestMapping("/user")
public class LibraryUserController {
    private final LibraryUserService libraryUserService;

    public LibraryUserController(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    @GetMapping("/overview")
    private String showUserOverview(Model datamodel) {
        datamodel.addAttribute("allUsers", libraryUserService.getAllUsers());
        datamodel.addAttribute("formUser", new NewLibraryUserDTO());
        datamodel.addAttribute("formModalHidden", true);

        return "userOverview";
    }

    @PostMapping("/save")
    private String saveOrUpdateUser(@ModelAttribute("formUser") NewLibraryUserDTO userDtoToBeSaved, BindingResult result,
                                    Model datamodel) {
        if (libraryUserService.usernameInUse(userDtoToBeSaved.getUsername())) {
            result.rejectValue("username", "duplicate", "This username is not available");
        }

        if (!userDtoToBeSaved.getPassword().equals(userDtoToBeSaved.getConfirmPassword())) {
            result.rejectValue("password", "no.match", "The passwords do not match");
        }

        if (result.hasErrors()) {
            datamodel.addAttribute("allUsers", libraryUserService.getAllUsers());
            datamodel.addAttribute("formModalHidden", false);
            return "userOverview";
        }

        libraryUserService.save(userDtoToBeSaved);
        return "redirect:/user/overview";
    }
}
