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
        assertNotNull(courses);
    }
}