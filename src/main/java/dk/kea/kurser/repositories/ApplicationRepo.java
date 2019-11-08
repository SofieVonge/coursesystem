package dk.kea.kurser.repositories;

import dk.kea.kurser.models.Application;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ApplicationRepo extends CrudRepository<Application, Long> {

    public Set<Application> findByCourse(Course course);
    public Set<Application> findByCourseOrderBySubmittedAtDesc(Course course);
    public Set<Application> findByStudent(User student);
}
