package dk.kea.kurser.controllers;

import dk.kea.kurser.models.Application;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.Role;
import dk.kea.kurser.models.User;
import dk.kea.kurser.services.CourseService;
import dk.kea.kurser.services.AdministrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @author Sofie Vonge Jensen
 * @since 11-11-2019
 * @version 1
 */

@Controller
public class AdministrationController {

    private AdministrationService administrationService;
    private CourseService courseService;

    /**
     * Constructor with injected services
     * @param administrationService
     * @param courseService
     */
    public AdministrationController(AdministrationService administrationService, CourseService courseService) {
        this.administrationService = administrationService;
        this.courseService = courseService;
    }


    /**
     * Shows all applications belonging to a specific course
     * @param id
     * @param model
     * @param session
     * @return review site
     */
   @GetMapping("/course/{id}/application")
   public String showApplications(@PathVariable("id") Long id, Model model, HttpSession session)
   {
       //gets the user
       User user = (User) session.getAttribute("user");

       //checks if the user is allowed to see the page
       if(user != null)
       {
           if(!user.getRole().equals(Role.ADMINISTRATOR))
           {
               return "redirect:/";
           }
       }

       //first we find the course
       Optional<Course> optionalCourse = courseService.findById(id);
       Course course = optionalCourse.get();
       //then we get the applications to that course
       //SHOULD WE ALSO ONLY FIND ONES WHICH STATUS ARE PENDING?
       model.addAttribute("applications", administrationService.findApplicationsByCourse(course));
       return "review";
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
            if(!user.getRole().equals(Role.ADMINISTRATOR))
            {
                return "redirect:/";
            }
        }
        //first we find the specific application
        Application application = administrationService.findApplicationById(id);
        //then we approve it
        administrationService.approveApplication(application);
        return "redirect:/review";
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
            if(!user.getRole().equals(Role.ADMINISTRATOR))
            {
                return "redirect:/";
            }
        }

        Application application = administrationService.findApplicationById(id);
        administrationService.rejectApplication(application);
        return "redirect:/review";
    }


}
