package dk.kea.kurser.legacy.services;

import dk.kea.kurser.legacy.models.Course;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service to make REST api calls for the course part of the system
 *
 * @author Andreas Dan Petersen
 * @since 11-11-2019
 * @version 1.0
 */
@Service
public class CourseApiService implements IApiService<Course> {

    private RestTemplate restTemplate;
    private String restUrl;

    public CourseApiService(RestTemplate restTemplate, String restUrl) {
        this.restTemplate = restTemplate;
        this.restUrl = restUrl;
    }

    /**
     * Fetches a list of all courses from the REST api service
     * @return a list of all courses, an empty list is returned if none were fetched
     */
    @Override
    public List<Course> listAll() {
        Course[] courses = restTemplate.getForObject(restUrl + "/course", Course[].class);

        if (courses != null) {
            return Arrays.asList(courses);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Adds a course to the REST api service
     * @param course the course to add
     */
    @Override
    public void add(Course course) {
        restTemplate.postForObject(restUrl + "/course", course, Course.class);
    }

    /**
     * Fetches a course from the REST api service with a given id
     * @param id the id of the course to fetch
     * @return the course fetched
     */
    @Override
    public Course findById(int id) {
        return restTemplate.getForObject(restUrl + "/course/" + id, Course.class);
    }

    /**
     * Updates a course in the REST api service
     * @param course the course to update
     */
    @Override
    public void update(Course course) {
        restTemplate.put(restUrl + "/course/" + course.getId(), course);
    }

    /**
     * Deletes a course from the REST api service
     * @param course the course to delete
     */
    @Override
    public void delete(Course course) {
        restTemplate.delete(restUrl + "/course/" + course.getId(), course);
    }

    /**
     * Checks if the REST api service has a course with a given id
     * @param id the id of the course to check
     * @return true if the course exists, false if not
     */
    @Override
    public boolean exists(int id) {
        //fetch resources as a simple string
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(restUrl + "/course/exist/" + id, String.class);
        //if status code is 204/no content, assume it exists
        return responseEntity.getStatusCode() == HttpStatus.NO_CONTENT;
    }
}
