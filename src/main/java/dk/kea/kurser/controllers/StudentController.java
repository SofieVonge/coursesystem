package dk.kea.kurser.controllers;

import dk.kea.kurser.models.Application;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.Role;
import dk.kea.kurser.models.User;
import dk.kea.kurser.services.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * @author Andreas Dan Petersen
 * @since 09-11-2019
 * @version 1.0
 */
@Controller
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Get mapping for the 'view course' site
     * @param id the path variable id to specify the course to view
     * @param model the view model for site
     * @return a string leading to the html site path
     */
    @GetMapping("/course/{id}/view")
    public String getCourse(@PathVariable("id") long id, Model model, HttpSession session) {

        User user = (User)session.getAttribute("user");

        if (user != null) {
            if (user.getRole().equals(Role.STUDENT)) {
                //fetch course from service
                Course course = studentService.findCourseById(id);
                //add to view model
                model.addAttribute("course", course);

                return "sites/course/view";
            }
        }

        return "redirect:/";
    }

    /**
     * Get mapping for the courses signed up overview site
     * @param model the view model for the site
     * @param session the current http session
     * @return a string leading to the html site path
     */
    @GetMapping("/application/signed-up")
    public String getSignedUp(Model model, HttpSession session) {
        User user = (User)session.getAttribute("user");

        if (user != null) {
            if (user.getRole().equals(Role.STUDENT)) {
                Set<Application> applications = studentService.findApplicationsByUser(user);

                model.addAttribute("user", user);
                model.addAttribute("applications", applications);

                return "sites/application/signed-up";
            }
        }

        return "redirect:/";
    }

    /**
     * Get mapping for the sign up site for a specific course
     * @param id the id of the course
     * @param session the http session
     * @return a string leading to the html site path
     */
    @GetMapping("/course/{id}/signup")
    public String submitSignUp(@PathVariable("id") long id, HttpSession session) {

        User user = (User)session.getAttribute("user");

        if (user != null) {
            if (user.getRole().equals(Role.STUDENT)) {

                studentService.signUpForCourse(user, id);

                //redirect to the signed up site
                return "redirect:/application/signed-up";
            }
        }

        return "redirect:/";
    }
}
