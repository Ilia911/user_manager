package by.yaromkin.user_manager.controller;

import by.yaromkin.user_manager.entity.UserAccount;
import by.yaromkin.user_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserAccount());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid UserAccount userAccountForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationError", "Invalid input data");
            return "registration";
        }

        if (!userService.saveUser(userAccountForm)) {
            model.addAttribute("registrationError", "User with such name has already exist");
            return "registration";
        }

        return "redirect:/";
    }
}
