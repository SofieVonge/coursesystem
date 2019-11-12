package dk.kea.kurser.legacy.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Course implements Serializable
{

    private String description;
    private String ects;
    private Integer id;
    private String languange;
    private Boolean mandatory;
    private String name;
    private String namedanish;
    private Integer numberOfTeachers;
    private Integer semester;
    private String studyprogramme;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -8968126658931647369L;

    public Course() { }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEcts() {
        return ects;
    }

    public void setEcts(String ects) {
        this.ects = ects;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLanguange() {
        return languange;
    }

    public void setLanguange(String languange) {
        this.languange = languange;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamedanish() {
        return namedanish;
    }

    public void setNamedanish(String namedanish) {
        this.namedanish = namedanish;
    }

    public Integer getNumberOfTeachers() {
        return numberOfTeachers;
    }

    public void setNumberOfTeachers(Integer numberOfTeachers) {
        this.numberOfTeachers = numberOfTeachers;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getStudyprogramme() {
        return studyprogramme;
    }

    public void setStudyprogramme(String studyprogramme) {
        this.studyprogramme = studyprogramme;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Course{" +
                "description='" + description + '\'' +
                ", ects='" + ects + '\'' +
                ", id=" + id +
                ", languange='" + languange + '\'' +
                ", mandatory=" + mandatory +
                ", name='" + name + '\'' +
                ", namedanish='" + namedanish + '\'' +
                ", numberOfTeachers=" + numberOfTeachers +
                ", semester=" + semester +
                ", studyprogramme='" + studyprogramme + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
