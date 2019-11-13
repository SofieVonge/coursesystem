package dk.kea.kurser.repositories;

import dk.kea.kurser.models.Application;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * <h1>A persistence interface for Course.</h1>
 *
 * @author Daniel Blom
 * @version 1.0
 * @since 2019-11-09
 * @see Course
 */
@Repository
public interface CourseRepository extends CrudRepository<Course, Long>, JpaSpecificationExecutor<Course>
{
    /**
     * Find a set of courses by specified teacher
     * @param teacher a user model
     * @return a set of courses
     * @see Course
     */
    Set<Course> findByTeachersContaining(User teacher);

    /**
     * Find a set of courses by specified student
     * @param student a user model
     * @return a set of courses
     * @see Course
     */
    Set<Course> findByStudentsContaining(User student);

    /**
     * Find a courses by specified application
     * @param application an application model
     * @return an optional Course
     * @see Course
     */
    Optional<Course> findByApplicationsContaining(Application application);
}