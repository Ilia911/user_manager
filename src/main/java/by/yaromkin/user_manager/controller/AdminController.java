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
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/new")
    public String fillNewUser(Model model) {
        model.addAttribute("userAccountForm", new UserAccount());
        return "new";
    }

    @PostMapping("/user/new")
    public String addUser(@ModelAttribute("userAccountForm") @Valid UserAccount userAccountForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationError", "Invalid input data");
            return "new";
        }

        if (!userService.saveUser(userAccountForm)){
            model.addAttribute("registrationError", "User with such name has already exist");
            return "list";
        }
        return "list";
    }
}
