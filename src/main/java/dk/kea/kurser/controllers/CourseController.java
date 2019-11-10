package dk.kea.kurser.controllers;

import dk.kea.kurser.dto.CourseSearch;
import dk.kea.kurser.helpers.CourseSpecifications;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.services.CourseService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public String displaySearch(Model model) {
        model.addAttribute("courseSearch", new CourseSearch());

        return "sites/course/search";
    }

    @PostMapping("course/search")
    public String submitSearch(@ModelAttribute("courseSearch") CourseSearch courseSearch, Model model) {
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
}
