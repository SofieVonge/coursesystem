package dk.kea.kurser.legacy.services;

import dk.kea.kurser.legacy.models.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StudentApiService {

    private RestTemplate restTemplate;
    private String restUrl;

    public StudentApiService(RestTemplate restTemplate, String restUrl) {
        this.restTemplate = restTemplate;
        this.restUrl = restUrl;
    }

    public Student findStudentById(long id) {
        return restTemplate.getForEntity(restUrl + "/student/" + id, Student.class).getBody();
    }
}
