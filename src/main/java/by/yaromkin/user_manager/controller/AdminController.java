package by.yaromkin.user_manager.controller;

import by.yaromkin.user_manager.entity.Role;
import by.yaromkin.user_manager.entity.UserAccount;
import by.yaromkin.user_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;

    @GetMapping("/user/new")
    public String fillNewUser(Model model) {
        model.addAttribute("userForm", new UserAccount());
        return "new";
    }

    @PostMapping("/user/new")
    public String addUser(@ModelAttribute("userForm") @Valid UserAccount userAccountForm,
                          BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationError", "Invalid input data");
            return "new";
        }

        if (!userService.saveUser(userAccountForm)) {
            model.addAttribute("registrationError", "User with such name has already exist");
            return "list";
        }
        return "redirect:/users";
    }

    @GetMapping("/user/{id}/edit")
    public String editUser(@PathVariable("id") long userId, Model model) {

        Optional<UserAccount> userAccount = userService.findUserById(String.valueOf(userId));

        if (userAccount.isPresent()) {
            model.addAttribute("userAccount", userAccount.get());
            model.addAttribute("roleUser", new Role(2, "ROLE_USER"));
            model.addAttribute("roleAdmin", new Role(1, "ROLE_ADMIN"));
            return "edit";
        }

        model.addAttribute("errorMessage", "Such user is not exist!");
        return userController.showUsers(model);
    }

    @PostMapping("/user/{id}/update")
    public String updateUser(@PathVariable("id") long id, @Valid UserAccount userAccount,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Invalid input data");
            model.addAttribute(userAccount);
            return "view";
        }

        userAccount.setCreatedAt(userService.findUserById(String.valueOf(id)).get().getCreatedAt());
        userAccount.setId(id);

        if (!userService.updateUser(userAccount)) {
            model.addAttribute("errorMessage", "User with such name has already exist");
            return "home_page";
        }
        return "redirect:/users";
    }

    @PostMapping("/user/lock")
    public String updateStatus(@RequestParam() String userId, Model model) {
        final Optional<UserAccount> userById = userService.findUserById(userId);

        if (userById.isPresent()) {
            UserAccount userAccount = userById.get();
            changeStatus(userAccount);
            userService.updateUser(userAccount);
        }
        return "redirect:/users";
    }

    private void changeStatus(UserAccount userAccount) {
        userAccount.setActive(!userAccount.isActive());
    }
}
