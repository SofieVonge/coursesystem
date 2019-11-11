package dk.kea.kurser.services;

import dk.kea.kurser.models.Application;
import dk.kea.kurser.models.ApplicationStatus;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.repositories.ApplicationRepo;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Sofie Vonge Jensen
 * @since 11-11-2019
 * @version 1
 */

@Service
public class AdministrationService {

    private ApplicationRepo applicationRepo;

    public AdministrationService(){}

    /**
     * Constructor with injected appicalionRepo
     * @param applicationRepo
     */
    public AdministrationService(ApplicationRepo applicationRepo) {
        this.applicationRepo = applicationRepo;
    }


    /**
     * Finds all applications belonging to a specific course ordered by submitted at
     * @param course
     * @return all applications belonging to the specific course
     */
    public Set<Application> findApplicationsByCourse(Course course)
    {
        Set<Application> applications = new HashSet<>();
        for(Application a : applicationRepo.findByCourseOrderBySubmittedAtDesc(course))
        {
            applications.add(a);
        }
        return applications;
    }

    /**
     * Approves the application and saves the new status to the repo
     * @param application
     */
    public void approveApplication(Application application)
    {
        application.setApplicationStatus(ApplicationStatus.ACCEPTED);
        applicationRepo.save(application);
    }

    /**
     * Rejects the application and saves the new status to the repo
     * @param application
     */
    public void rejectApplication(Application application)
    {
        application.setApplicationStatus(ApplicationStatus.REJECTED);
        applicationRepo.save(application);
    }

    /**
     * Finds an application by its id
     * @param id
     * @return the specific application
     */
    public Application findApplicationById(Long id)
    {
        Optional<Application> application = applicationRepo.findById(id);

        if(!application.isPresent())
        {
            throw new RuntimeException("No application found");
        }

        return application.get();
    }
/*
    public Set<User> seeStudentsBasedOnApplicationForSpecificCourse(Course course)
    {
        Set<Application> applications = applicationRepo.findByCourseOrderBySubmittedAtDesc(course);
        Set<User> students = new HashSet<>();

        for(Application a : applications)
        {
            students.add(a.getStudent());
        }

        return students;

    }*/

    /* might need for later. See all students in a specific course
    public Set<User> seeStudentsBasedOnCourse(Course course)
    {
        Set<User> students = userRepository.findByEnrolledCourses(course);
        if(students.isEmpty())
        {
            throw new RuntimeException("No students found for this course");
        }

        return students;
    }*/
}
