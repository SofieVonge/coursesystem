package dk.kea.kurser.controllers;

import dk.kea.kurser.models.Application;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.User;
import dk.kea.kurser.services.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.Set;

@Controller
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/course/{id}/view")
    public String getCourse(@PathVariable("id") long id, Model model) {
        //fetch course from service
        Course course = studentService.findCourseById(id);
        //add to view model
        model.addAttribute("course", course);

        return "sites/course/view";
    }

    @GetMapping("/application/signed-up")
    public String getSignedUp(Model model, HttpSession session) {
        User user = (User)session.getAttribute("user");
        Set<Application> applications = studentService.findApplicationsByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("applications", applications);

        return "sites/application/signed-up";
    }

    @GetMapping("/course/{id}/signup")
    public String submitSignUp(@PathVariable("id") long id, HttpSession session) {

        User user = (User)session.getAttribute("user");
        studentService.signUpForCourse(user, id);

        //redirect to the signed up site
        return "redirect:/application/signed-up";
    }
}
