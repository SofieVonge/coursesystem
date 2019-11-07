package dk.kea.kurser.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "applications")
public class Application extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private User student;

    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    private Timestamp submittedAt;

    @Enumerated(value = EnumType.STRING)
    private ApplicationStatus applicationStatus;

    public Application() { }

    public Application(User student, Course course, Timestamp submittedAt, ApplicationStatus applicationStatus) {
        this.student = student;
        this.course = course;
        this.submittedAt = submittedAt;
        this.applicationStatus = applicationStatus;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Timestamp getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Timestamp submittedAt) {
        this.submittedAt = submittedAt;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}
