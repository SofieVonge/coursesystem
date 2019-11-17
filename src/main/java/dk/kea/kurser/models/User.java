package dk.kea.kurser.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "email", length = 20, unique = true)
    private String email;

    @Column(name = "secret", length = 32)
    private String secret;

    @Column(name = "first_name", length = 20)
    private String firstName;

    @Column(name = "last_name", length = 20)
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Set<Application> applications = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, mappedBy = "students")
    private Set<Course> enrolledCourses = new HashSet<>(); //for students

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, mappedBy = "teachers")
    private Set<Course> teachingCourses = new HashSet<>(); //for teachers

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "createdBy")
    private Set<Course> createdCourses;

    public User(String email, String secret, String firstName, String lastName, Role role,
                Set<Application> applications, Set<Course> enrolledCourses,
                Set<Course> createdCourses, Set<Course> teachingCourses) {

        this.email = email;
        this.secret = secret;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.applications = applications;
        this.enrolledCourses = enrolledCourses;
        this.teachingCourses = teachingCourses;
        this.createdCourses = createdCourses;
    }

    public User(){}

    public Set<Course> getCreatedCourses() {
        return createdCourses;
    }

    public void setCreatedCourses(Set<Course> createdCourses) {
        this.createdCourses = createdCourses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public Set<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(Set<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public Set<Course> getTeachingCourses() {
        return teachingCourses;
    }

    public void setTeachingCourses(Set<Course> teachingCourses) {
        this.teachingCourses = teachingCourses;
    }
}
