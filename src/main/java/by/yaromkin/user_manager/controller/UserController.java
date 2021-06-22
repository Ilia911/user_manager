package by.yaromkin.user_manager.controller;

import by.yaromkin.user_manager.entity.UserAccount;
import by.yaromkin.user_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("userAccounts", userService.findAllUsers());
        return "list";
    }

    @GetMapping("/user")
    public String showUser(@RequestParam() String userId, Model model) {
        final Optional<UserAccount> userById = userService.findUserById(userId);

        if (userById.isPresent()) {
            model.addAttribute("userAccount", userById.get());
            return "view";
        }
        model.addAttribute("errorMessage", "Such user is not exist!");
        return "view";
    }

    @GetMapping("/user/5")
    public String showUser(Model model) {
        final Optional<UserAccount> userById = userService.findUserById(Long.toString(5));

        if (userById.isPresent()) {
            model.addAttribute("userAccount", userById.get());
            return "view";
        }
        model.addAttribute("errorMessage", "Such user is not exist!");
        return "view";
    }

}
