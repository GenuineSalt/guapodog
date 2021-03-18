package guapodog;

import guapodog.entity.Developer;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;

@Repository
public interface IDeveloperRepository extends PageableRepository <Developer, Long> {
}