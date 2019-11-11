package dk.kea.kurser.legacy.services;

import dk.kea.kurser.legacy.models.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StudentApiService {

    private RestTemplate restTemplate;
    private String restUrl;

    public StudentApiService(RestTemplate restTemplate, String restUrl) {
        this.restTemplate = restTemplate;
        this.restUrl = restUrl;
    }

    public List<Student> findAll() {
        Student[] students = restTemplate.getForEntity(restUrl + "/student", Student[].class).getBody();

        if (students != null) {
            return Arrays.asList(students);
        } else {
            return new ArrayList<>();
        }
    }

    public Student findStudentById(long id) {
        return restTemplate.getForObject(restUrl + "/student/" + id, Student.class);
    }
}
