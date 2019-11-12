package dk.kea.kurser.legacy.services;

import dk.kea.kurser.legacy.models.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service to make REST api calls for the student part of the system
 *
 * @author Andreas Dan Petersen
 * @since 11-11-2019
 * @version 1.0
 */
@Service
public class StudentApiService {

    private RestTemplate restTemplate;
    private String restUrl;

    public StudentApiService(RestTemplate restTemplate, String restUrl) {
        this.restTemplate = restTemplate;
        this.restUrl = restUrl;
    }

    /**
     * Fetches a list of all students from the REST api service
     * @return a list of all students, an empty list is returned if none were fetched
     */
    public List<Student> listAll() {
        Student[] students = restTemplate.getForObject(restUrl + "/student", Student[].class);

        if (students != null) {
            return Arrays.asList(students);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Fetches a student from the REST api service with a given id
     * @param id the id of the student to fetch
     * @return the student that was returned from the service, null if none was found
     */
    public Student findStudentById(int id) {
        return restTemplate.getForObject(restUrl + "/student/" + id, Student.class);
    }

    /**
     * Updates the REST api service with a POST request to update a new student
     * @param student the student to update in the REST api service
     */
    public void updateStudent(Student student) {
        restTemplate.put(restUrl + "/student/" + student.getId(), student);
    }
}
