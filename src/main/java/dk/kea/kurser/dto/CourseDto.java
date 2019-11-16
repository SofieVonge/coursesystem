package dk.kea.kurser.dto;

import dk.kea.kurser.legacy.models.Teacher;
import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.StudyProgram;
import dk.kea.kurser.models.User;

import java.util.ArrayList;
import java.util.List;

public class CourseDto
{
    private Course course;
    private List<User> teachers = new ArrayList<>();
    private List<StudyProgram> studyPrograms = new ArrayList<>();

    public CourseDto(Course course, List<User> teachers, List<StudyProgram> studyPrograms) {
        this.course = course;
        this.teachers = teachers;
        this.studyPrograms = studyPrograms;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<User> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<User> teachers) {
        this.teachers = teachers;
    }

    public List<StudyProgram> getStudyPrograms() {
        return studyPrograms;
    }

    public void setStudyPrograms(List<StudyProgram> studyPrograms) {
        this.studyPrograms = studyPrograms;
    }
}
