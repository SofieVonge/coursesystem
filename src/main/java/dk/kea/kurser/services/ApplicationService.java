package dk.kea.kurser.services;

import dk.kea.kurser.models.Application;
import dk.kea.kurser.models.ApplicationStatus;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.User;
import dk.kea.kurser.repositories.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Service for the application part of the system
 *
 * @author Sofie, Désirée, Daniel & Andreas
 * @since 13-11-2019
 * @version 1.0
 */
@Service
public class ApplicationService {

    private ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    /**
     * Finds all applications belonging to a specific course ordered by submitted at
     * @param course
     * @return all applications belonging to the specific course with status PENDING
     */
    public Set<Application> findApplicationsByCourse(Course course)
    {
        Set<Application> allApplications = applicationRepository.findByCourseOrderBySubmittedAtDesc(course);
        Set<Application> pendingApplications = new HashSet<>();

       for(Application a : allApplications)
       {
           if(a.getApplicationStatus().equals(ApplicationStatus.PENDING))
           {
               pendingApplications.add(a);
           }
       }

        return pendingApplications;
    }

    /**
     * Approves the application and saves the new status to the repo
     * @param application
     */
    public void approveApplication(Application application)
    {
        application.setApplicationStatus(ApplicationStatus.ACCEPTED);
        applicationRepository.save(application);
    }

    /**
     * Rejects the application and saves the new status to the repo
     * @param application
     */
    public void rejectApplication(Application application)
    {
        application.setApplicationStatus(ApplicationStatus.REJECTED);
        applicationRepository.save(application);
    }

    /**
     * Finds an application by its id
     * @param id
     * @return the specific application
     */
    public Application findApplicationById(Long id)
    {
        Optional<Application> application = applicationRepository.findById(id);

        if(!application.isPresent())
        {
            throw new RuntimeException("No application found");
        }

        return application.get();
    }

    /**
     * Finds a set of applications from a given user as author
     * @param student the author of the application
     * @return a set holding a collection of all applications with user as author
     */
    public Set<Application> findApplicationsByUser(User student) {
        return applicationRepository.findByStudent(student);
    }

    /**
     * Signs up a user for a given course
     * @param student the user that wishes to sign up for the course
     * @param course the course to sign up for
     */
    public void signUpForCourse(User student, Course course) {

        //if user has already signed up, return
        for (Application application : student.getApplications()) {
            if (application.getCourse() == course) {
                return;
            }
        }

        //create new application and set values
        Application application = new Application();
        application.setApplicationStatus(ApplicationStatus.PENDING);
        application.setCourse(course);
        application.setStudent(student);
        application.setSubmittedAt(new Timestamp(System.currentTimeMillis()));

        //save application
        applicationRepository.save(application);
    }
}
