package dk.kea.kurser.services;

import dk.kea.kurser.models.Course;
import dk.kea.kurser.repositories.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service that manages the course part of the system
 *
 * @author Désirée, Sofie, Daniel & Andreas
 * @since 09-11-2019
 * @version 1.0
 */
@Service
public class CourseService
{
    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Iterable<Course> findAll() {
        return courseRepository.findAll();
    }

    public Page<Course> findAll(Specification<Course> specification, Pageable pageable) {
        return courseRepository.findAll(specification, pageable);
    }

    public List<Course> findAll(Specification<Course> specification) {
        return courseRepository.findAll(specification);
    }

    public Course findById(Long id) {
        Optional<Course> result = courseRepository.findById(id);

        return result.orElse(null);
    }

    /**
     * Finds a given course from a given id
     * @param id the id of course to find
     * @return the course that holds the specific id
     */
    public Course findCourseById(long id) {
        Optional<Course> courses = courseRepository.findById(id);
        return courses.orElse(null);
    }

    public void createCourse (Course course){
        courseRepository.save(course);
    }

    //metoden opdater kursus
    public void updateCourse(Course course){

        courseRepository.save(course);
    }

    //den metode sletter kursus
    public void deleteCourse(Long id){

        courseRepository.deleteById(id);
    }
}
