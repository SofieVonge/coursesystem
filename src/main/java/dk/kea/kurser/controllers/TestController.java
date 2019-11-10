package dk.kea.kurser.controllers;

import dk.kea.kurser.models.Role;
import dk.kea.kurser.models.User;
import dk.kea.kurser.services.StudentService;
import dk.kea.kurser.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class TestController
{
    private UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("test/newuser")
    public String newUser() {

        User newUser = new User();

        newUser.setFirstName("Daniel");
        newUser.setLastName("Blom");
        newUser.setEmail("mail@blomsites.dk");
        newUser.setSecret("minhemmelighed");
        newUser.setRole(Role.STUDENT);

        userService.saveUser(newUser);

        return "redirect:/";
    }
}
