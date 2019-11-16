package dk.kea.kurser.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * Controller that handles the session authentication
 *
 * @author Andreas Dan Petersen
 * @since 09-11-2019
 * @version 1.0
 */
@Controller
public class AuthenticationController {

    public AuthenticationController() { }

    /**
     * Get mapping for the index/login page
     * @return a string leading to the html site path
     */
    @GetMapping(value = {"/login"})
    public String login() {
        return "sites/index";
    }

    /**
     * Get mapping for the logout feature
     * @return a string leading to the index/login get mapping
     */
    @GetMapping("/logout")
    public String logout() {
        return "sites/index";
    }
}
