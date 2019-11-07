package dk.kea.kurser.controllers;

import dk.kea.kurser.dto.Credentials;
import dk.kea.kurser.models.Role;
import dk.kea.kurser.services.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    private AuthService authService;

    public IndexController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(value = {"", "/", "/index", })
    public String getIndex(Model model, HttpSession session) {
        //if user is already logged in, redirect to the 'search course' site
        if (session.getAttribute("role") != null) {
            return "sites/course/search";
        }

        //add credentials dto to model
        model.addAttribute("credentials", new Credentials());

        return "sites/index";
    }

    @PostMapping("/login")
    public String submitLogin(@ModelAttribute("credentials") Credentials credentials, HttpSession session) {
        Role role = authService.authenticatedAs(credentials);

        //if role returned IS null, user was not authenticated
        if (role == null) {
            return "redirect:/";
        } else {
            session.setAttribute("email", credentials.getEmail());
            session.setAttribute("role", role);
            return "/course/search";
        }
    }
}
