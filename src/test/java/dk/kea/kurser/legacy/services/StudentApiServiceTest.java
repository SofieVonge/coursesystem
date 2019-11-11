package dk.kea.kurser.legacy.services;

import dk.kea.kurser.legacy.models.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentApiServiceTest {

    @Autowired
    private StudentApiService studentApiService;

    @Test
    void findAll() {
        List<Student> students = studentApiService.findAll();
        assertNotNull(students);
    }

    @Test
    void findStudentById() {

    }
}