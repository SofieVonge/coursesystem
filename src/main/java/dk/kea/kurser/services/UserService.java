package dk.kea.kurser.services;

import dk.kea.kurser.models.User;
import dk.kea.kurser.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for the user part of the system
 *
 * @author Sofie, Daniel & Désirée
 * @since 10-11-2019
 * @version 1.0
 */
@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public User findUserById (Long id)
    {
        //findById giver en optional
        Optional<User> userOptional=userRepository.findById(id);

        //returner user
        return userOptional.orElse(null);
    }

    /*public Set<User> seeStudentsBasedOnApplicationForSpecificCourse(Course course)
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
