package dk.kea.kurser.legacy.services;

import dk.kea.kurser.legacy.models.Course;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CourseApiService {

    private RestTemplate restTemplate;
    private String restUrl;

    public CourseApiService(RestTemplate restTemplate, String restUrl) {
        this.restTemplate = restTemplate;
        this.restUrl = restUrl;
    }

    public List<Course> listAll() {
        Course[] courses = restTemplate.getForEntity(restUrl + "/course", Course[].class).getBody();

        if (courses != null) {
            return Arrays.asList(courses);
        } else {
            return new ArrayList<Course>();
        }
    }
}
