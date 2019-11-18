package dk.kea.kurser.services;

import dk.kea.kurser.legacy.models.Teacher;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.Role;
import dk.kea.kurser.models.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * Service that adds compatibility for the legacy rest api services with the rest of system
 *
 * @author Andreas Dan Petersen
 * @since 18-11-2019
 * @version 1.0
 */
@Service
public class RestApiModelService {

    public RestApiModelService() { }

    public Course makeNonLegacy(dk.kea.kurser.legacy.models.Course legacyCourse, User author) {
        //init new course to fill with data from legacy model
        Course course = new Course();

        //set values from legacy model
        course.setId((long)legacyCourse.getId());
        course.setTitleDanish(legacyCourse.getNamedanish());
        course.setTitleEnglish(legacyCourse.getName());
        course.setSemester(legacyCourse.getSemester());
        course.setEcts(Integer.parseInt(legacyCourse.getEcts()));
        course.setMandatory(legacyCourse.getMandatory());
        course.setContent(legacyCourse.getDescription());
        course.setSpokenLanguage(legacyCourse.getLanguange());

        //set the rest with default values
        //sets
        course.setStudyPrograms(new HashSet<>());
        course.setStudents(new HashSet<>());
        course.setTeachers(new HashSet<>());
        course.setApplications(new HashSet<>());

        //strings and integers
        course.setCreatedBy(author);
        course.setStudentsMin(0);
        course.setStudentsExpected(0);
        course.setStudentsMax(0);
        course.setPrerequisites("undefined");
        course.setLearningOutcome("undefined");
        course.setLearningActivities("undefined");
        course.setExamForm("undefined");
        course.setClassCode("undefined");

        return course;
    }

    public User makeNonLegacy(Teacher teacher) {
        return null;
    }

    public dk.kea.kurser.legacy.models.Course makeLegacy(Course course) {
        return null;
    }

    public Teacher makeLegacy(User user) throws Exception {
        //make sure user is teacher
        if (user.getRole() != Role.TEACHER) {
            throw new Exception("Wrong user role for teacher conversion.");
        }

        return null;
    }
}
