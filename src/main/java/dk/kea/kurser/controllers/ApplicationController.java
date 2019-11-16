package dk.kea.kurser.controllers;

import dk.kea.kurser.models.Application;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.Role;
import dk.kea.kurser.models.User;
import dk.kea.kurser.services.ApplicationService;
import dk.kea.kurser.services.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.Set;

@Controller
public class ApplicationController {

    private ApplicationService applicationService;
    private CourseService courseService;

    public ApplicationController(ApplicationService applicationService, CourseService courseService) {
        this.applicationService = applicationService;
        this.courseService = courseService;
    }

    /**
     * Shows all applications belonging to a specific course
     * @param id
     * @param model
     * @param session
     * @return review site
     */
    @GetMapping("/course/{id}/applications")
    public String showApplications(@PathVariable("id") Long id, Model model, HttpSession session)
    {
        //gets the user
        User user = (User) session.getAttribute("user");

        //checks if the user is allowed to see the page
        if(user != null)
        {
            if(user.getRole().equals(Role.ADMINISTRATOR))
            {
                //first we find the course
                Course course = courseService.findById(id);
                //then we get the applications to that course
                model.addAttribute("course", course);
                model.addAttribute("applicationList", applicationService.findApplicationsByCourse(course));
                return "sites/application/review";
            }
        }
        return "redirect:/";
    }


    /**
     * Accepts the application
     * @param id
     * @param session
     * @return redirect to the review
     */
    @GetMapping("/application/{id}/accept")
    public String acceptApplication(@PathVariable("id") Long id, HttpSession session)
    {
        User user = (User) session.getAttribute("user");

        if(user != null)
        {
            if(user.getRole().equals(Role.ADMINISTRATOR))
            {
                //first we find the specific application
                Application application = applicationService.findApplicationById(id);
                //then we approve it
                applicationService.approveApplication(application);
                return "redirect:/review";
            }
        }

        return "redirect:/";
    }

    /**
     * Deny the application
     * @param id
     * @param session
     * @return redirect to the review
     */
    @GetMapping("/application/{id}/deny")
    public String denyApplication(@PathVariable("id") Long id, HttpSession session)
    {
        User user = (User) session.getAttribute("user");

        if(user != null)
        {
            if(user.getRole().equals(Role.ADMINISTRATOR))
            {
                Application application = applicationService.findApplicationById(id);
                applicationService.rejectApplication(application);
                return "redirect:/review";
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

        //user must be authenticated as a student
        if (user != null) {
            if (user.getRole().equals(Role.STUDENT)) {
                Course course = courseService.findCourseById(id);
                if (course != null) {
                    applicationService.signUpForCourse(user, course);
                }

                //redirect to the signed up site
                return "redirect:/application/signed-up";
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

        //user must be authenticated as a student
        if (user != null) {
            if (user.getRole().equals(Role.STUDENT)) {
                Set<Application> applications = applicationService.findApplicationsByUser(user);

                model.addAttribute("user", user);
                model.addAttribute("applicationList", applications);

                return "sites/application/signed-up";
            }
        }

        return "redirect:/";
    }
}
