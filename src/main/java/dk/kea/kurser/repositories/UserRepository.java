package dk.kea.kurser.repositories;

import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.Role;
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

    public Set<User> findByEnrolledCourses(Course course);

    public Set<User> findByRole(Role role);

    public User findByEmail(String email);
}
