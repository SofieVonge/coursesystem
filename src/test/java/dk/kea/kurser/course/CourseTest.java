package dk.kea.kurser.course;

import dk.kea.kurser.helpers.CourseSpecifications;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.services.CourseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public class CourseTest
{
    @Autowired
    private CourseService courseService;

    @BeforeAll
    public void init() {

    }

    @Test
    public void findCourseByPartialTitle_AllLocales() {
        String title = "Test Titel";
        Specification<Course> courseSpecification = CourseSpecifications.titleLike(title);

        List<Course> courses = courseService.findAll(Specification.where(courseSpecification));

        // assert stuff
    }

    public void findCoursesByPage() {
        Pageable pageable = PageRequest.of(2, 40, Sort.by("id"));
        Page<Course> page = courseService.findAll(null, pageable);

        // assert stuff
    }
}
