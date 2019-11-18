package dk.kea.kurser.dto;

import dk.kea.kurser.models.Application;
import org.springframework.data.domain.Page;

import java.util.List;

public class ApplicationSearchDto {
    public enum SortMethod
    {
        SUBMIT_TIME,
        COURSE;
    }

    private SortMethod sortMethod;
    private Page<Application> applications;

    public ApplicationSearchDto() {
        this.sortMethod = SortMethod.SUBMIT_TIME;
        this.applications = null;
    }

    public ApplicationSearchDto(SortMethod sortMethod, Page<Application> applications) {
        this.sortMethod = sortMethod;
        this.applications = applications;
    }

    public SortMethod getSortMethod() {
        return sortMethod;
    }

    public void setSortMethod(SortMethod sortMethod) {
        this.sortMethod = sortMethod;
    }

    public Page<Application> getApplications() {
        return applications;
    }

    public void setApplications(Page<Application> applications) {
        this.applications = applications;
    }
}
