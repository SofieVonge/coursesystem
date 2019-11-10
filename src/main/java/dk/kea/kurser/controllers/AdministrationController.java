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
   @GetMapping("/course/{id}/application")
   public String showApplications(@PathVariable("id") Long id, Model model)
   {
       Optional<Course> optionalCourse = courseRepo.findById(id);
       Course course = optionalCourse.get();
       model.addAttribute("applications", administrationService.findApplicationsByCourse(course));
       return "review";
   }


   @GetMapping("/application/{id}/accept")
    public String acceptApplication(@PathVariable("id") Long id)
    {
        Application application = administrationService.findApplicationById(id);
        administrationService.approveApplication(application);
        return "redirect:/review";
    }

    @GetMapping("/application/{id}/deny")
    public String denyApplication(@PathVariable("id") Long id)
    {
        Application application = administrationService.findApplicationById(id);
        administrationService.rejectApplication(application);
        return "redirect:/review";
    }


}
