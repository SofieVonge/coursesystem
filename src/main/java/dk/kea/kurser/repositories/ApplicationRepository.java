package dk.kea.kurser.repositories;

import dk.kea.kurser.models.Application;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Sofie Vonge Jensen
 * @since 11-11-2019
 * @version 1
 */


@Repository
public interface ApplicationRepository extends CrudRepository<Application, Long> {

    public Set<Application> findByCourse(Course course);

    /**
     * Finds a set of applications by their course ordered by the submitted time
     * @param course
     * @return a set of applications
     */
    public Set<Application> findByCourseOrderBySubmittedAtDesc(Course course);

    public Set<Application> findByStudent(User student);
}
