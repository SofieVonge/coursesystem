package dk.kea.kurser.controllers;

import dk.kea.kurser.models.Application;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.repositories.CourseRepo;
import dk.kea.kurser.services.AdministrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AdministrationController {

    private AdministrationService administrationService;
    private CourseRepo courseRepo;

    public AdministrationController(AdministrationService administrationService, CourseRepo courseRepo) {
        this.administrationService = administrationService;
        this.courseRepo = courseRepo;
    }


    //this method shows all application belonging to a specific course
    //should this be here or...? what should it return?
   @GetMapping("/course/{id}/application")
   public String showApplications(@PathVariable("id") Long id, Model model)
   {
       Optional<Course> optionalCourse = courseRepo.findById(id);
       Course course = optionalCourse.get();
       model.addAttribute("applications", administrationService.findApplicationsByCourse(course));
       return "review";
   }


   //this is setup for the postmapping, finding the one application to accept or deny
   @GetMapping("/application/{id}")
   public String handleApplication(@PathVariable("id") Long id, Model model)
   {
       model.addAttribute("application", administrationService.findApplicationById(id));
       return "review"; //what should it return?
   }

    @PostMapping("/accept")
    public String acceptApplication(@ModelAttribute Application application)
    {
        administrationService.approveApplication(application);
        return "redirect:/review"; //redirect to what?
    }

    @PostMapping("/deny")
    public String denyApplication(@ModelAttribute Application application)
    {
        administrationService.rejectApplication(application);
        return "redirect:/review"; //redirect to what?
    }
}
