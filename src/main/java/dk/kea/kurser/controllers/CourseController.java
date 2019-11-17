package dk.kea.kurser.controllers;

import dk.kea.kurser.dto.CourseDto;
import dk.kea.kurser.dto.CourseSearch;
import dk.kea.kurser.helpers.CourseSpecifications;
import dk.kea.kurser.helpers.UserComparator;
import dk.kea.kurser.models.*;
import dk.kea.kurser.services.CourseService;
import dk.kea.kurser.services.UserService;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@SessionAttributes({"email", "role"})
@Controller
public class CourseController
{

    private CourseService courseService;
    private UserService userService;


    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @ModelAttribute("email")
    public String email(Principal principal) {
        return principal.getName();
    }

    @ModelAttribute("role")
    public Role role(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
        return userPrincipal.getRole();
    }

    @GetMapping(value = { "", "/", "/index", "course/search"})
    public String displaySearch(Model model) {

        User user = userService.findUserByEmail((String)model.getAttribute("email"));
        if (!(user.getRole().equals(Role.TEACHER) || user.getRole().equals(Role.STUDENT))) {
            return "redirect:/";
        }

        model.addAttribute("courseSearch", new CourseSearch());
        return "sites/course/search";
    }

    @PostMapping("course/search")
    public String submitSearch(@ModelAttribute("courseSearch") CourseSearch courseSearch, Model model) {

        User user = userService.findUserByEmail((String)model.getAttribute("email"));

        if (!(user.getRole().equals(Role.TEACHER) || user.getRole().equals(Role.STUDENT))) {
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
    public String displayCourse(@PathVariable("id") long id, Model model) {

        if (!(model.getAttribute("role").equals(Role.TEACHER) || model.getAttribute("role").equals(Role.STUDENT))) {
            return "redirect:/";
        }

        //fetch course from service
        Course course = courseService.findCourseById(id);
        //add to view model
        model.addAttribute("course", course);

        return "sites/course/view";
    }

    @GetMapping("/course/create")
    public String displayCreateCourse(Model model) {

        if (!model.getAttribute("role").equals(Role.TEACHER)) {
            return "redirect:/";
        }

        Course course = new Course();
        List<User> teachers = new ArrayList<User>(userService.listTeachers());
        List<StudyProgram>studyPrograms = Arrays.asList(StudyProgram.values());

        model.addAttribute("courseDTO", new CourseDto(course, teachers,studyPrograms));
        model.addAttribute("user", userService.findUserByEmail((String)model.getAttribute("email"))); //re-insert

        return "sites/course/create";
    }

    @PostMapping("/course/create")
    public String submitCreateCourse(@ModelAttribute("courseDto") CourseDto courseDto, Model model) {

        Course course = null;

        if (!model.getAttribute("role").equals(Role.TEACHER)) {
            return "redirect:/";
        }

        //re-inserted user here
        User user = userService.findUserByEmail((String)model.getAttribute("email"));

        // remove gaps in dto lists..
        // remove null objects
        courseDto.getTeachers().removeAll(Collections.singleton(null));
        courseDto.getStudyPrograms().removeAll(Collections.singleton(null));
        // remove objects with null ids
        Iterator<User> userIterator = courseDto.getTeachers().iterator();
        while (userIterator.hasNext())
        {
            User usera = userIterator.next();
            if(usera.getId() == null)
                courseDto.getTeachers().remove(usera);
        }

        // prep course for saving
        // get course from dto
        course = courseDto.getCourse();
        // add specified teachers to course
        course.setTeachers(new HashSet<User>(courseDto.getTeachers()));
        // add specified study programs to course
        course.setStudyPrograms(new HashSet<StudyProgram>(courseDto.getStudyPrograms()));
        // set course created by to current user
        course.setCreatedBy(user);

        // save course
        courseService.createCourse(course);

        //bed browser om at navigere til index-siden
        return "redirect:/";
    }

    @GetMapping("/course/{id}/delete")
    public String submitDeleteCourse(@PathVariable("id") Long id, Model model) {

        if (!model.getAttribute("role").equals(Role.TEACHER)) {
            return "redirect:/";
        }

        if(courseService.canChange(id, userService.findUserByEmail((String)model.getAttribute("email")).getId())) {
            courseService.deleteCourse(id);
        }

        return "redirect:/";
    }

    @GetMapping("/course/{id}/update")
    public String displayUpdateCourse(@PathVariable("id") Long id, Model model) {

        // if user doesn't have role permission to be here
        if (!(model.getAttribute("role").equals(Role.TEACHER))) {
            return "redirect:/";
        }

        // if user doesn't have permission to update course
        if(!courseService.canChange(id, userService.findUserByEmail((String)model.getAttribute("email")).getId())) {
            return "redirect:/";
        }

        Course course = courseService.findCourseById(id);
        List<User> teachers = new ArrayList<User>(userService.listTeachers());
        List<StudyProgram>studyPrograms = Arrays.asList(StudyProgram.values());

        model.addAttribute("courseDTO", new CourseDto(course, teachers,studyPrograms));
        model.addAttribute("user", userService.findUserByEmail((String)model.getAttribute("email"))); //re-insert

        return "sites/course/update";
    }

    @PostMapping("/course/update")
    public String submitUpdateCourse(@ModelAttribute("courseDto") CourseDto courseDto, Model model) {
        User user = null;
        Course course = null;

        // if user doesn't have role permission to be here
        if (!(model.getAttribute("role").equals(Role.TEACHER))) {
            return "redirect:/";
        }

        //re-inserted user here
        user = userService.findUserByEmail((String)model.getAttribute("email"));

        // if user doesn't have permission to update course
        if(!courseService.canChange(courseDto.getCourse().getId(), user.getId())) {
            return "redirect:/";
        }

        // remove gaps in dto lists..
        // remove null objects
        courseDto.getTeachers().removeAll(Collections.singleton(null));
        courseDto.getStudyPrograms().removeAll(Collections.singleton(null));
        // remove objects with null ids
        Iterator<User> userIterator = courseDto.getTeachers().iterator();
        while (userIterator.hasNext())
        {
            User usera = userIterator.next();
            if(usera.getId() == null)
                courseDto.getTeachers().remove(usera);
        }

        // prep course for saving
        // get course from dto
        course = courseDto.getCourse();
        // add specified teachers to course
        course.setTeachers(new HashSet<User>(courseDto.getTeachers()));
        // add specified study programs to course
        course.setStudyPrograms(new HashSet<StudyProgram>(courseDto.getStudyPrograms()));

        course.setApplications(null);
        course.setStudents(null);

        // save course
        courseService.updateCourse(course);

        //bed browser om at navigere til index-siden
        return "redirect:/";
    }
}
