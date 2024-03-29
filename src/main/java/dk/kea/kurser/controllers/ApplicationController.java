package dk.kea.kurser.controllers;

import dk.kea.kurser.dto.ApplicationSearchDto;
import dk.kea.kurser.models.*;
import dk.kea.kurser.services.ApplicationService;
import dk.kea.kurser.services.CourseService;
import dk.kea.kurser.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SessionAttributes({"email", "role"})
@Controller
public class ApplicationController {

    private ApplicationService applicationService;
    private CourseService courseService;
    private UserService userService;

    public ApplicationController(ApplicationService applicationService, CourseService courseService,
                                 UserService userService) {

        this.applicationService = applicationService;
        this.courseService = courseService;
        this.userService = userService;
    }

    @ModelAttribute("email")
    public String email(Principal principal) {
        return principal.getName();
    }

    @ModelAttribute("role")
    public Role role(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getRole();
    }

    /**
     * Shows all applications belonging to a specific course
     *
     * @param id
     * @param model
     * @return review site
     */
    @GetMapping("/course/{id}/applications")
    public String showApplications(@PathVariable("id") Long id, Model model) {
        //checks if the user is allowed to see the page
        if (model.getAttribute("role").equals(Role.ADMINISTRATOR)) {
            //first we find the course
            Course course = courseService.findById(id);
            //then we get the applications to that course
            model.addAttribute("course", course);
            model.addAttribute("applicationList", applicationService.findApplicationsByCourse(course));
            return "sites/application/review";
        }
        return "redirect:/";
    }


    /**
     * Accepts the application
     *
     * @param id
     * @return redirect to the review
     */
    @GetMapping("/application/{id}/accept")
    public String acceptApplication(@PathVariable("id") Long id, Model model) {
        if (model.getAttribute("role").equals(Role.ADMINISTRATOR)) {
            //first we find the specific application
            Application application = applicationService.findApplicationById(id);
            //then we approve it
            applicationService.approveApplication(application);
            return "redirect:/";
        }

        return "redirect:/";
    }

    /**
     * Deny the application
     *
     * @param id
     * @return redirect to the review
     */
    @GetMapping("/application/{id}/deny")
    public String denyApplication(@PathVariable("id") Long id, Model model) {
        if (model.getAttribute("role").equals(Role.ADMINISTRATOR)) {
            Application application = applicationService.findApplicationById(id);
            applicationService.rejectApplication(application);
            return "redirect:/";
        }

        return "redirect:/";
    }

    /**
     * Get mapping for the sign up site for a specific course
     *
     * @param id the id of the course
     * @return a string leading to the html site path
     */
    @GetMapping("/course/{id}/signup")
    public String submitSignUp(@PathVariable("id") long id, Model model) {

        //user must be authenticated as a student
        if (model.getAttribute("role").equals(Role.STUDENT)) {
            Course course = courseService.findCourseById(id);
            if (course != null) {
                User user = userService.findUserByEmail((String) model.getAttribute("email"));
                applicationService.signUpForCourse(user, course);
            }

            //redirect to the signed up site
            return "redirect:/application/signed-up";
        }

        return "redirect:/";
    }

    /**
     * Get mapping for the courses signed up overview site
     *
     * @param model the view model for the site
     * @return a string leading to the html site path
     */
    @GetMapping("/application/signed-up")
    public String getSignedUp(Model model) {

        //user must be authenticated as a student
        if (model.getAttribute("role").equals(Role.STUDENT)) {
            User user = userService.findUserByEmail((String) model.getAttribute("email"));
            Set<Application> applications = applicationService.findApplicationsByUser(user);

            model.addAttribute("user", user);
            model.addAttribute("applicationList", applications);

            return "sites/application/signed-up";
        }

        return "redirect:/";
    }

    @GetMapping("/application/list")
    public String displayList(@RequestParam Map<String,String> requestParams, Model model) {

        int page = 0;
        int size = 10;
        Sort sort = Sort.by("submittedAt").ascending();
        Pageable pageable = null;

        if(requestParams.containsKey("page")) {
            try {
                page = Integer.parseInt(requestParams.get("page")) - 1;
                if(page < 0)
                    page = 0;
            } catch (NumberFormatException nfe) {
                /* nothing to see here */
            }
        }

        if(requestParams.containsKey("size")) {
            try {
                size = Integer.parseInt(requestParams.get("size"));
            } catch (NumberFormatException nfe) {
                /* nothing to see here */
            }
        }

        if(requestParams.containsKey("sort")) {
            String sortByParam = requestParams.get("sort");

            if(sortByParam.toLowerCase().equals(ApplicationSearchDto.SortMethod.COURSE.name().toLowerCase())) {
                sort = Sort.by("course.id").and(Sort.by("submittedAt").ascending()).ascending();
            }
        }

        pageable = PageRequest.of(page, size, sort);

        Page<Application> applicationPage = applicationService.listPage(pageable);

        int adjacent = 4;
        int minPage = Math.max(1, (int)Math.min(applicationPage.getTotalPages() - adjacent, (page + 1) - Math.ceil((double)(adjacent / 2))));
        int maxPage = minPage + 4;


        model.addAttribute("minPage", minPage);
        model.addAttribute("maxPage", maxPage);

        model.addAttribute("applicationSearchDto",
                new ApplicationSearchDto(
                        ApplicationSearchDto.SortMethod.SUBMIT_TIME,
                        applicationPage));

        return "sites/application/list";
    }
}
