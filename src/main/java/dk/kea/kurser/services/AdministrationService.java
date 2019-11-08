package dk.kea.kurser.services;

import dk.kea.kurser.models.Application;
import dk.kea.kurser.models.ApplicationStatus;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.User;
import dk.kea.kurser.repositories.ApplicationRepo;
import dk.kea.kurser.repositories.CourseRepo;
import dk.kea.kurser.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AdministrationService {

    private UserRepository userRepository;
    private CourseRepo courseRepo;

    public AdministrationService(){}

    public AdministrationService(UserRepository userRepository, CourseRepo courseRepo) {
        this.userRepository = userRepository;
        this.courseRepo = courseRepo;
    }


    public void approveApplication(Application application)
    {
        application.setApplicationStatus(ApplicationStatus.ACCEPTED);
    }

    public void rejectApplication(Application application)
    {
        application.setApplicationStatus(ApplicationStatus.REJECTED);
    }

    public Set<User> seeStudentsBasedOnCourse(Course course)
    {
        Set<User> students = userRepository.findByEnrolledCourses(course);
        if(students.isEmpty())
        {
            throw new RuntimeException("No students found for this course");
        }

        return students;
    }
}
