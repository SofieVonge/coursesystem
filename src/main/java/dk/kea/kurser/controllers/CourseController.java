package dk.kea.kurser.controllers;

import dk.kea.kurser.dto.CourseSearch;
import dk.kea.kurser.helpers.CourseSpecifications;
import dk.kea.kurser.legacy.models.Teacher;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.Role;
import dk.kea.kurser.models.StudyProgram;
import dk.kea.kurser.models.User;
import dk.kea.kurser.services.CourseService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController
{

    private CourseService courseService;


    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("course/search")
    public String displaySearch(Model model, HttpSession session) {
        User user = (User)session.getAttribute("user");

        if (user == null ||
                !(user.getRole().equals(Role.TEACHER) || user.getRole().equals(Role.STUDENT))) {
            return "redirect:/";
        }

        model.addAttribute("courseSearch", new CourseSearch());
        //add user to view model to correctly display nav menu
        model.addAttribute("user", user);
        return "sites/course/search";
    }

    @PostMapping("course/search")
    public String submitSearch(@ModelAttribute("courseSearch") CourseSearch courseSearch, Model model, HttpSession session) {
        User user = (User)session.getAttribute("user");

        if (user == null ||
                !(user.getRole().equals(Role.TEACHER) || user.getRole().equals(Role.STUDENT))) {
            return "redirect:/";
        }

        // holds fetched courses
        List<Course> courses = new ArrayList<>();
        // final course specification
        Specification<Course> courseSpecification = null;
        // will hold course specification fragments
        List<Specification<Course>> courseSpecificationsFragments = new ArrayList<>();

        // add title specification fragment
        if (!courseSearch.getTitle().isEmpty())
            courseSpecificationsFragments.add(CourseSpecifications.titleLike(courseSearch.getTitle()));

        // add elective/mandatory specification fragment
        if (courseSearch.isElective() && !courseSearch.isMandatory())
            courseSpecificationsFragments.add(CourseSpecifications.isElective());
        else if(courseSearch.isMandatory() && !courseSearch.isElective())
            courseSpecificationsFragments.add(CourseSpecifications.isMandatory());

        // add first search specification
        if(courseSpecificationsFragments.size() > 0)
            courseSpecification = Specification.where(courseSpecificationsFragments.get(0));

        // concat search specifications
        for (Specification<Course> spec : courseSpecificationsFragments) {
            courseSpecification = courseSpecification.and(spec);
        }

        // fetch courses from search specifications
        courses = courseService.findAll(courseSpecification);

        // add courses to courseSearch dto
        courseSearch.setCourses(courses);

        // add courseSearch dto to view model
        model.addAttribute("courseSearch", courseSearch);

        return "sites/course/search";
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

        if (user == null ||
                !(user.getRole().equals(Role.TEACHER) || user.getRole().equals(Role.STUDENT))) {
            return "redirect:/";
        }

        //fetch course from service
        Course course = courseService.findCourseById(id);
        //add to view model
        model.addAttribute("course", course);

        return "sites/course/view";
    }

    @GetMapping("/course/create")
    public String create(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null ||
                !(user.getRole().equals(Role.TEACHER))) {
            return "redirect:/";
        }

        model.addAttribute("teachers", new ArrayList<Teacher>());
        model.addAttribute("studyPrograms", new ArrayList<StudyProgram>());

        return "sites/course/create";
    }

    @PostMapping("/course/create")
    public String createCourse(Model model, HttpSession session) {
        Course course = null;
        User user = (User) session.getAttribute("user");

        if (user == null ||
                !(user.getRole().equals(Role.TEACHER))) {
            return "redirect:/";
        }

        course = new Course();

        course.setTitleEnglish((String)model.getAttribute("titleEnglish"));
        course.setEcts((int)model.getAttribute("ects"));


        courseService.createCourse(course);
        //bed browser om at navigere til index-siden
        return "redirect:/";
    }

    @GetMapping("/course/{id}/delete")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null ||
                !(user.getRole().equals(Role.TEACHER))) {
            return "redirect:/";
        }

        courseService.deleteCourse(id);
        return "redirect:/";
    }

    // få fat id fra stien vha. @PathVariable
    @GetMapping("/course/{id}/update")
    public String update(@PathVariable("id") Long id, Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null ||
                !(user.getRole().equals(Role.TEACHER))) {
            return "redirect:/";
        }

        //tilføj course med id til viewmodel
        model.addAttribute("course", courseService.findById(id));

        return "sites/course/update";
    }
}
