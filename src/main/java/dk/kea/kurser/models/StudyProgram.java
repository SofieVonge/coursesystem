package dk.kea.kurser.models;

import javax.persistence.Transient;

public enum StudyProgram
{
    COMPUTER_SCIENCE("Computer Science"),
    IT_SECURITY("IT-Security"),
    SOFTWARE_DEVELOPMENT("Software Development"),
    WEB_DEVELOPMENT("Web Development");

    @Transient
    public final String label;

    StudyProgram(final String label) {
        this.label = label;
    }

    public final String getLabel() {
        return label;
    }
}
