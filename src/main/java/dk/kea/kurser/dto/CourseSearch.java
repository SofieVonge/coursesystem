package dk.kea.kurser.dto;

import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.StudyProgram;

import java.util.ArrayList;
import java.util.List;

public class CourseSearch
{
    private String title;
    private boolean elective;
    private boolean mandatory;
    private List<Course> courses;

    public CourseSearch() {
        title = "";
        elective = true;
        mandatory = true;
        courses = new ArrayList<>();
    }

    public CourseSearch(String title, boolean elective, boolean mandatory, List<Course> courses) {
        this.title = title;
        this.elective = elective;
        this.mandatory = mandatory;
        this.courses = courses;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isElective() {
        return elective;
    }

    public void setElective(boolean elective) {
        this.elective = elective;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
