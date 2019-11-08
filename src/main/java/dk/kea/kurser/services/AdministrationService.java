package dk.kea.kurser.services;

import dk.kea.kurser.models.Application;
import dk.kea.kurser.models.ApplicationStatus;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.User;
import dk.kea.kurser.repositories.ApplicationRepo;
import dk.kea.kurser.repositories.CourseRepo;
import dk.kea.kurser.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AdministrationService {

    private UserRepository userRepository;
    private CourseRepo courseRepo;
    private ApplicationRepo applicationRepo;

    public AdministrationService(){}

    public AdministrationService(UserRepository userRepository, CourseRepo courseRepo, ApplicationRepo applicationRepo) {
        this.userRepository = userRepository;
        this.courseRepo = courseRepo;
        this.applicationRepo = applicationRepo;
    }


    public Set<Application> findApplicationsByCourse(Course course)
    {
        Set<Application> applications = new HashSet<>();
        for(Application a : applicationRepo.findByCourse(course))
        {
            applications.add(a);
        }
        return applications;
    }

    public void approveApplication(Application application)
    {
        application.setApplicationStatus(ApplicationStatus.ACCEPTED);
        applicationRepo.save(application);
    }

    public void rejectApplication(Application application)
    {
        application.setApplicationStatus(ApplicationStatus.REJECTED);
        applicationRepo.save(application);
    }

    public Application findApplicationById(Long id)
    {
        Optional<Application> application = applicationRepo.findById(id);

        if(!application.isPresent())
        {
            throw new RuntimeException("No application found");
        }

        return application.get();
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
