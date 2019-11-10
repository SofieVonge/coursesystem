package dk.kea.kurser.controllers;

import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.Role;
import dk.kea.kurser.models.StudyProgram;
import dk.kea.kurser.models.User;
import dk.kea.kurser.services.StudentService;
import dk.kea.kurser.services.TeacherService;
import dk.kea.kurser.services.UserService;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashSet;

@Controller()
public class TestController
{
    private UserService userService;
    private TeacherService teacherService;

    public TestController(UserService userService, TeacherService teacherService) {
        this.userService = userService;
        this.teacherService = teacherService;
    }

    @GetMapping("test/student/new")
    public String newStudent() {

        User student = new User();

        student.setFirstName("Daniel");
        student.setLastName("Blom");
        student.setEmail("mail@blomsites.dk");
        student.setSecret("minhemmelighed");
        student.setRole(Role.STUDENT);

        userService.saveUser(student);

        return "redirect:/";
    }

    @GetMapping("test/teacherandcourse/new")
    public String newTeacher() {
        User teacher = new User();

        teacher.setFirstName("Jarl");
        teacher.setLastName("Tuxen");
        teacher.setEmail("jart@kea.dk");
        teacher.setSecret("sinhemmelighed");
        teacher.setRole(Role.TEACHER);



        Course course = new Course();
        course.setClassCode("classCode");
        course.setContent("content");
        course.setEcts(10);
        course.setExamForm("examForm");
        course.setLearningActivities("learningActivities");
        course.setMandatory(false);
        course.setLearningOutcome("learningOutcome");
        course.setPrerequisites("prerequisites");
        course.setSemester(2);
        course.setSpokenLanguage("spokenLanguage");
        course.setStudentsExpected(15);
        course.setStudentsMin(10);
        course.setStudentsMax(25);
        course.setStudyPrograms(new HashSet<>(Arrays.asList(StudyProgram.WEB_DEVELOPMENT, StudyProgram.IT_SECURITY)));
        course.setTitleDanish("Test titel");
        course.setTitleEnglish("Test title");

        course.setCreatedBy(teacher);

        teacher.setTeachingCourses(new HashSet<>(Arrays.asList(course)));

        userService.saveUser(teacher);

        //teacherService.creatCourse(course);

        return "redirect:/";
    }
}
