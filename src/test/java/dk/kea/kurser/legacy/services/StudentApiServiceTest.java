package dk.kea.kurser.legacy.services;

import dk.kea.kurser.legacy.models.Counselor;
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
    void listAll() {
        List<Student> students = studentApiService.listAll();
        assertTrue(students.size() > 0);
    }

    @Test
    void findStudentById() {
    }

    @Test
    void updateStudent() {

        Student student = new Student();
        student.setId(3);
        student.setCounselor(new Counselor());
        student.setEmail("gruppe@awesome.test");
        student.setName("Full name");
        student.setEnabled(1);
        student.setPassword("det er en hemmelighed");
        student.setUsername("gruppeawesome");
    }
}