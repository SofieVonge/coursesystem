package dk.kea.kurser.services;

import dk.kea.kurser.models.Application;
import dk.kea.kurser.models.ApplicationStatus;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.User;
import dk.kea.kurser.repositories.ApplicationRepo;
import dk.kea.kurser.repositories.CourseRepo;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;

/**
 * @author Andreas Dan Petersen
 * @since 09-11-2019
 * @version 1.0
 */
@Service
public class StudentService {

    private CourseRepo courseRepo;
    private ApplicationRepo applicationRepo;

    public StudentService(CourseRepo courseRepo, ApplicationRepo applicationRepo) {
        this.courseRepo = courseRepo;
        this.applicationRepo = applicationRepo;
    }

    /**
     * Finds a given course from a given id
     * @param id the id of course to find
     * @return the course that holds the specific id
     */
    public Course findCourseById(long id) {
        Optional<Course> courses = courseRepo.findById(id);
        return courses.orElse(null);
    }

    /**
     * Finds a set of applications from a given user as author
     * @param student the author of the application
     * @return a set holding a collection of all applications with user as author
     */
    public Set<Application> findApplicationsByUser(User student) {
        return applicationRepo.findByStudent(student);
    }

    /**
     * Signs up a user for a given course
     * @param user the user that wishes to sign up for the course
     * @param id the id of the course to sign up for
     * @return a boolean value indicating whether or not the sign up was successful
     */
    public boolean signUpForCourse(User user, long id) {
        try {
            //fetch course from id
            Course course = findCourseById(id);

            //create new application and set values
            Application application = new Application();
            application.setApplicationStatus(ApplicationStatus.PENDING);
            application.setCourse(course);
            application.setStudent(user);
            application.setSubmittedAt(new Timestamp(System.currentTimeMillis()));

            //save application
            applicationRepo.save(application);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
