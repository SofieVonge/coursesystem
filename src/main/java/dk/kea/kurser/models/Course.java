package dk.kea.kurser.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course extends BaseEntity
{
    @OneToMany(fetch = FetchType.LAZY,
            cascade = {
            CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "course")
    private Set<Application> applications = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "students__courses",
            joinColumns = { @JoinColumn(name = "course_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") } )
    private Set<User> students = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "teachers__courses",
            joinColumns = { @JoinColumn(name = "course_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") } )
    private Set<User> teachers = new HashSet<>();

    @ElementCollection(targetClass = StudyProgram.class)
    @CollectionTable(name = "courses__study_programs", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "study_program")
    private Set<StudyProgram> studyPrograms = new HashSet<>();

    public Course() { }

    public Course(Set<User> students, Set<User> teachers) {
        this.students = students;
        this.teachers = teachers;
    }

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    public Set<User> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<User> teachers) {
        this.teachers = teachers;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public Set<StudyProgram> getStudyPrograms() {
        return studyPrograms;
    }

    public void setStudyPrograms(Set<StudyProgram> studyPrograms) {
        this.studyPrograms = studyPrograms;
    }
}
