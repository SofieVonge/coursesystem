package dk.kea.kurser.legacy.services;

import dk.kea.kurser.legacy.models.Teacher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service to make REST api calls for the teacher part of the system
 *
 * @author Andreas Dan Petersen
 * @since 12-11-2019
 * @version 1.0
 */
@Service
public class TeacherApiService implements IApiService<Teacher> {

    private RestTemplate restTemplate;
    private String restUrl;

    public TeacherApiService(RestTemplate restTemplate, String restUrl) {
        this.restTemplate = restTemplate;
        this.restUrl = restUrl;
    }

    /**
     * Fetches a list of all teachers from the REST api service
     * @return a list of all teachers, an empty list is returned if none were fetched
     */
    @Override
    public List<Teacher> listAll() {
        Teacher[] teachers = restTemplate.getForObject(restUrl + "/teacher", Teacher[].class);

        if (teachers != null) {
            return Arrays.asList(teachers);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Adds a teacher to the REST api service
     * @param teacher the teacher to add
     */
    @Override
    public void add(Teacher teacher) {
        restTemplate.postForObject(restUrl + "/teacher", teacher, Teacher.class);
    }

    /**
     * Fetches a teacher from the REST api service with a given id
     * @param id the id of the teacher to fetch
     * @return the teacher fetched
     */
    @Override
    public Teacher findById(int id) {
        return restTemplate.getForObject(restUrl + "/teacher/" + id, Teacher.class);
    }

    /**
     * Updates a teacher in the REST api service
     * @param teacher the teacher to update
     */
    @Override
    public void update(Teacher teacher) {
        restTemplate.put(restUrl + "/teacher/" + teacher.getId(), teacher);
    }

    /**
     * Deletes a teacher from the REST api service
     * @param teacher the teacher to delete
     */
    @Override
    public void delete(Teacher teacher) {
        restTemplate.delete(restUrl + "/teacher/" + teacher.getId(), teacher);
    }

    /**
     * Checks if the REST api service has a teacher with a given id
     * @param id the id of the teacher to check
     * @return true if the teacher exists, false if not
     */
    @Override
    public boolean exists(int id) {
        //fetch resources as a simple string
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(restUrl + "/teacher/exist/" + id, String.class);
        //if status code is 204/no content, assume it exists
        return responseEntity.getStatusCode() == HttpStatus.NO_CONTENT;
    }
}
