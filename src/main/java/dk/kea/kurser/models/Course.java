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
    @CollectionTable(name = "courses__study_program", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "study_program")
    private Set<StudyProgram> studyPrograms = new HashSet<>();

    private int semester;
    @Column(name = "class_code")
    private String classCode;
    private boolean mandatory;
    private int ects;
    @Column(name = "spoken_language")
    private String spokenLanguage;
    @Column(name = "students_min")
    private int studentsMin;
    @Column(name = "students_max")
    private int studentsMax;
    @Column(name = "students_expected")
    private int studentsExpected;

    @Lob
    private String prerequisites;

    @Column(name = "learning_outcome")
    @Lob
    private String learningOutcome;

    @Lob
    private String content;

    @Column(name = "learning_activities")
    @Lob
    private String learningActivities;

    @Column(name = "exam_form")
    @Lob
    private String examForm;

    @Column(name = "title_english")
    private String titleEnglish;
    @Column(name = "title_danish")
    private String titleDanish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;


    public Course() { }

    public Course(Set<Application> applications, Set<User> students, Set<User> teachers, Set<StudyProgram> studyPrograms, int semester, String classCode, boolean mandatory, int ects, String spokenLanguage, int studentsMin, int studentsMax, int studentsExpected, String prerequisites, String learningOutcome, String content, String learningActivities, String examForm, String titleEnglish, String titleDanish, User createdBy) {
        this.applications = applications;
        this.students = students;
        this.teachers = teachers;
        this.studyPrograms = studyPrograms;
        this.semester = semester;
        this.classCode = classCode;
        this.mandatory = mandatory;
        this.ects = ects;
        this.spokenLanguage = spokenLanguage;
        this.studentsMin = studentsMin;
        this.studentsMax = studentsMax;
        this.studentsExpected = studentsExpected;
        this.prerequisites = prerequisites;
        this.learningOutcome = learningOutcome;
        this.content = content;
        this.learningActivities = learningActivities;
        this.examForm = examForm;
        this.titleEnglish = titleEnglish;
        this.titleDanish = titleDanish;
        this.createdBy = createdBy;
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

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public String getSpokenLanguage() {
        return spokenLanguage;
    }

    public void setSpokenLanguage(String spokenLanguage) {
        this.spokenLanguage = spokenLanguage;
    }

    public int getStudentsMin() {
        return studentsMin;
    }

    public void setStudentsMin(int studentsMin) {
        this.studentsMin = studentsMin;
    }

    public int getStudentsMax() {
        return studentsMax;
    }

    public void setStudentsMax(int studentsMax) {
        this.studentsMax = studentsMax;
    }

    public int getStudentsExpected() {
        return studentsExpected;
    }

    public void setStudentsExpected(int studentsExpected) {
        this.studentsExpected = studentsExpected;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getLearningOutcome() {
        return learningOutcome;
    }

    public void setLearningOutcome(String learningOutcome) {
        this.learningOutcome = learningOutcome;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLearningActivities() {
        return learningActivities;
    }

    public void setLearningActivities(String learningActivities) {
        this.learningActivities = learningActivities;
    }

    public String getExamForm() {
        return examForm;
    }

    public void setExamForm(String examForm) {
        this.examForm = examForm;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public String getTitleDanish() {
        return titleDanish;
    }

    public void setTitleDanish(String titleDanish) {
        this.titleDanish = titleDanish;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
