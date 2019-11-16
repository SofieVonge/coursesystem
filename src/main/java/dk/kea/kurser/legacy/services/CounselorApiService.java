package dk.kea.kurser.legacy.services;

import dk.kea.kurser.legacy.models.Counselor;
import dk.kea.kurser.legacy.models.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service to make REST api calls for the counselor part of the system
 *
 * @author Andreas Dan Petersen
 * @since 16-11-2019
 * @version 1.0
 */
@Service
public class CounselorApiService {

    private RestTemplate restTemplate;
    private String restUrl;

    public CounselorApiService(RestTemplate restTemplate, String restUrl) {
        this.restTemplate = restTemplate;
        this.restUrl = restUrl;
    }

    /**
     * Fetches a list of all counselors from the REST api service
     * @return a list of all counselors, an empty list is returned if none were fetched
     */
    public List<Counselor> listAll() {
        Counselor[] counselors = restTemplate.getForObject(restUrl + "/counselor", Counselor[].class);

        if (counselors != null) {
            return Arrays.asList(counselors);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Adds a counselor to the REST api service
     * @param counselor the counselor to add
     */
    public void addCounselor(Counselor counselor) {
        restTemplate.postForObject(restUrl + "/counselor", counselor, Counselor.class);
    }

    /**
     * Fetches a counselor from the REST api service with a given id
     * @param id the id of the counselor to fetch
     * @return the counselor fetched
     */
    public Counselor findCounselorById(int id) {
        return restTemplate.getForObject(restUrl + "/counselor/" + id, Counselor.class);
    }

    /**
     * Updates a counselor in the REST api service
     * @param counselor the counselor to update
     */
    public void updateCounselor(Counselor counselor) {
        restTemplate.put(restUrl + "/counselor/" + counselor.getId(), counselor);
    }

    /**
     * Deletes a counselor from the REST api service
     * @param counselor the counselor to delete
     */
    public void deleteCounselor(Counselor counselor) {
        restTemplate.delete(restUrl + "/counselor/" + counselor.getId(), counselor);
    }

    /**
     * Fetches a list of students associated by a counselor id
     * @param id the id of the counselor
     * @return a list of students
     */
    public List<Student> studentsByCounselorId(int id) {
        Student[] legacyStudents = restTemplate.getForObject(restUrl + "/counselor/" + id + "/students", Student[].class);
        if (legacyStudents != null) {
            return Arrays.asList(legacyStudents);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Checks if the REST api service has a counselor with a given id
     * @param id the id of the counselor to check
     * @return true if the counselor exists, false if not
     */
    public boolean counselorExists(int id) {
        //fetch resources as a simple string
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(restUrl + "/counselor/exist/" + id, String.class);
        //if status code is 204/no content, assume it exists
        return responseEntity.getStatusCode() == HttpStatus.NO_CONTENT;
    }
}
