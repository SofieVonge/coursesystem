package dk.kea.kurser.repositories;

import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CourseRepo extends CrudRepository<Course, Long> {

    public Set<User> findByStudents(Course course);
}
