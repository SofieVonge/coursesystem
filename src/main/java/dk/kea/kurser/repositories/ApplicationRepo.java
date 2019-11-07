package dk.kea.kurser.repositories;

import dk.kea.kurser.models.Application;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepo extends CrudRepository<Application, Long> {
}
