package dk.kea.kurser.services;

import dk.kea.kurser.models.Course;
import dk.kea.kurser.repositories.CourseRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;

public class CourseService
{
    private CourseRepo courseRepository;

    public Iterable<Course> findAll() {
        return courseRepository.findAll();
    }

    public Page<Course> findAll(Specification<Course> specification, Pageable pageable) {
        return courseRepository.findAll(specification, pageable);
    }

    public List<Course> findAll(Specification<Course> specification) {
        return courseRepository.findAll(specification);
    }
}
