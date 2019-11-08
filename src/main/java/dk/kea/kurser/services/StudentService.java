package dk.kea.kurser.services;

import dk.kea.kurser.models.Application;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.User;
import dk.kea.kurser.repositories.ApplicationRepo;
import dk.kea.kurser.repositories.CourseRepo;
import org.springframework.stereotype.Service;

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
}
