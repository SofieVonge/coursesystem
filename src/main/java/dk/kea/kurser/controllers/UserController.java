package dk.kea.kurser.controllers;

import dk.kea.kurser.models.Role;
import dk.kea.kurser.models.User;
import dk.kea.kurser.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * Service that manages the user part of the system
 *
 * @author Désirée
 * @since 13-09-2019
 * @version 1.0
 */
@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}/update")
    public String getUpdate(@PathVariable("id") long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (user.getRole().equals(Role.ADMINISTRATOR)) {
                //user is the session user, mUser is the model
                model.addAttribute("mUser", userService.findUserById(id));
                return "sites/user/update";
            }
        }

        return "redirect:/";
    }

    @PostMapping("/user/update")
    public String submitUpdate(@ModelAttribute User mUser, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (user.getRole().equals(Role.ADMINISTRATOR)) {
                userService.saveUser(mUser);
                return "redirect:/";
            }
        }

        return "redirect:/";
    }
}
