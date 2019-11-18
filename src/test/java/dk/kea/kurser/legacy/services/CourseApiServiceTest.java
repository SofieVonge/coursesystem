package dk.kea.kurser.legacy.services;

import dk.kea.kurser.legacy.models.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseApiServiceTest {

    @Autowired
    private CourseApiService courseApiService;

    @Test
    void listAll() {
        List<Course> courses = courseApiService.listAll();
        System.out.println(courses);

        assertNotNull(courses);
    }

    @Test
    void addCourse() {

    }

    @Test
    void findCourseById() {
        for (int i = 0; i < 10; i++) {
            try {
                if (courseApiService.exists(i)) {
                    Course course = courseApiService.findById(i);
                    System.out.println(course);
                    assertNotNull(course);
                }
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    @Test
    void updateCourse() {
    }

    @Test
    void deleteCourse() {
    }

}