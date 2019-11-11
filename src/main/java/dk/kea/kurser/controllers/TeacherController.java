package dk.kea.kurser.controllers;


import dk.kea.kurser.models.Course;
import dk.kea.kurser.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TeacherController {

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    TeacherService teacherService;

@GetMapping("/create")
    public String create(){

    return "createCourse";
}
@PostMapping("/create")
    public String createCourse(@ModelAttribute Course course){

    teacherService.createCourse(course);
    //bed browser om at navigere til index-siden
    return "redirect:/";
}
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id)
    {
        teacherService.deleteTeacher(id);
        return "redirect:/";
    }

    // få fat id fra stien vha. @PathVariable
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model){
        //tilføj course med id til viewmodel
        model.addAttribute("course", teacherService.findById(id));
        return "update";
    }

    // opdater product - @ModelAttribute bruges til at få fat i product fra post
    @PostMapping("/update")
    public String updateTeacher(@ModelAttribute Course course){
        //kald update service
        teacherService.updateCourse(course);
        //sikr mod refresh fejl og sletter igen
        return "redirect:/";
    }

}
