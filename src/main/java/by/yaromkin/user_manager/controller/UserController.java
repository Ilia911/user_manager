package by.yaromkin.user_manager.controller;

import by.yaromkin.user_manager.entity.UserAccount;
import by.yaromkin.user_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String showUsers(Model model) {
        model.addAttribute("userAccounts", userService.findAllUsers());
        model.addAttribute("userAccountForId", new UserAccount());
        return "list";
    }

    @GetMapping("/view")
    public String showUser(@RequestParam() String userId, Model model) {
        final Optional<UserAccount> userById = userService.findUserById(userId);

        if (userById.isPresent()) {
            model.addAttribute("userAccount", userById.get());
            return "view";
        }
        model.addAttribute("errorMessage", "Such user is not exist!");
        return "view";


/*        model.addAttribute("title", "userId");
        model.addAttribute("message", userId);
        return "home_page";*/
    }

}
