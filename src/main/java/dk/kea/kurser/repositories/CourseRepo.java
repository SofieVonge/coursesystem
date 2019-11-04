package dk.kea.kurser.repositories;

import dk.kea.kurser.models.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepo extends CrudRepository<Course, Long> {
}
