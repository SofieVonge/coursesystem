package dk.kea.kurser.repositories;

import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Andreas Dan Petersen, Sofie Vonge Jensen
 * @since 07-11-2019
 * @version 1.0
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Finds a user based off an email and secret combination, used for authentication purposes
     * @param email the given email for the user
     * @param secret the given password for the user
     * @return the user associated with the email and secret combination.
     */
    public User findByEmailAndSecret(String email, String secret);

    public Set<User> findByEnrolledCourses(Course course);
}
