package dk.kea.kurser.controllers;

import dk.kea.kurser.dto.Credentials;
import dk.kea.kurser.models.User;
import dk.kea.kurser.services.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author Andreas Dan Petersen
 * @since 09-11-2019
 * @version 1.0
 */
@Controller
public class IndexController {

    private AuthService authService;

    public IndexController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Get mapping for the index/login page
     * @param model view model for the web site
     * @param session the current http session
     * @return a string leading to the html site path
     */
    @GetMapping(value = {"", "/", "/index"})
    public String getLogin(Model model, HttpSession session) {
        //if user is already logged in, redirect to the 'search course' site
        if (session.getAttribute("user") != null) {
            return "sites/course/search";
        }

        //add credentials dto to model
        model.addAttribute("credentials", new Credentials());

        return "sites/index";
    }

    /**
     * Post mapping for the login site
     * @param credentials the credentials dto holding the authentication info
     * @param session the current http session
     * @return a string leading to the html site path
     */
    @PostMapping("/login")
    public String submitLogin(@ModelAttribute("credentials") Credentials credentials, HttpSession session) {
        //authenticate as user from auth service
        User user = authService.authenticatedAs(credentials);

        //if user returned IS null, user was not authenticated
        if (user == null) {
            return "redirect:/";
        } else {
            //add authenticated user info to http session
            session.setAttribute("user", user);

            //redirect to search course site
            return "sites/course/search";
        }
    }

    /**
     * Get mapping for the logout feature
     * @param session the http session
     * @return a string leading to the index/login get mapping
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //clear session then redirect to index
        session.removeAttribute("user");
        return "redirect:/";
    }
}
