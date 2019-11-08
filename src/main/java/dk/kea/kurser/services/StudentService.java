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

@Service
public class StudentService {

    private CourseRepo courseRepo;
    private ApplicationRepo applicationRepo;

    public StudentService(CourseRepo courseRepo, ApplicationRepo applicationRepo) {
        this.courseRepo = courseRepo;
        this.applicationRepo = applicationRepo;
    }

    public Course findCourseById(long id) {
        Optional<Course> courses = courseRepo.findById(id);
        return courses.orElse(null);
    }

    public Set<Application> findApplicationsByUser(User student) {
        return applicationRepo.findByStudent(student);
    }

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
