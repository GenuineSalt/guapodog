package guapodog;

import guapodog.entity.Developer;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.PageableRepository;

@Repository
public interface IDeveloperRepository extends PageableRepository <Developer, String> {
    
    @Executable
    Iterable<Developer> findByName(String name, Pageable pageable);

    @Executable
    Iterable<Developer> findByTeam(String name, Pageable pageable);

    @Executable
    Iterable<Developer> findByNameAndTeam(String name, String team, Pageable pageable);
}