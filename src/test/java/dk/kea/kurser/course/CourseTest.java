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
        // test title
        String title = "est tite";
        Specification<Course> courseSpecification = CourseSpecifications.titleLike(title);

        // fetch list of courses from specification
        List<Course> courses = courseService.findAll(Specification.where(courseSpecification));

        // assert one of the titles in each course contains specified test title
    }

    @Test
    public void findCourseByPartialTitle_AllLocales_AndByEcts() {
        // test title
        String title = "est tite";
        // ects extrema
        int ectsMin = 10;
        int ectsMax = 20;

        // build specification query
        Specification<Course> courseSpecification =
                CourseSpecifications.titleLike(title)
                .and(CourseSpecifications.ectsBetween(ectsMin, ectsMax));

        // fetch list of courses from specification
        List<Course> courses = courseService.findAll(courseSpecification);

        // assert one of the titles in each course contains specified test title
        // assert each course has ects value between specified ects extrema
    }

    @Test
    public void findCourseByEcts() {
        // ects points
        int ects = 10;

        // build specification query
        Specification<Course> courseSpecification =
                CourseSpecifications.ectsEquals(ects);

        List<Course> courses = courseService.findAll(courseSpecification);

        // assert all courses found has ects value that equals specified ects points.
    }

    @Test
    public void findCoursesByPage() {
        Pageable pageable = PageRequest.of(2, 40, Sort.by("id"));
        Page<Course> page = courseService.findAll(null, pageable);

        // assert stuff page-wise
    }
}
