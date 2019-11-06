package dk.kea.kurser.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
public class Application extends BaseEntity {

    private Student student;
    private Course course;
    private LocalDateTime signedUpTime;
}
