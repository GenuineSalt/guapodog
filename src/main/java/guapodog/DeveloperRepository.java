package guapodog;

import java.sql.Timestamp;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import guapodog.entity.Developer;
import guapodog.exception.NotFoundException;
import io.micronaut.data.model.Pageable;

@Singleton 
public class DeveloperRepository {

    @Inject
    IDeveloperRepository developerRepository;

    public Developer findById(String id) throws NotFoundException {
        return developerRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Iterable<Developer> findAll(Pageable pageable, String name, String team) {
        return findDevelopers(name, team, pageable);
    }

    @Transactional
    public Developer updateDeveloper(Developer req, String id) {
        Developer developer = developerRepository.findById(id).orElseThrow(NotFoundException::new);
        update(req, developer);
        return developerRepository.update(developer);
    }

    public Developer saveDeveloper(Developer developer) {
        return developerRepository.save(developer);
    }
            
    public void deleteDeveloperById(String id) {
        developerRepository.deleteById(id);
    }

    private void update(Developer req, Developer dev) {
        if (req.getName() != null) {
            dev.setName(req.getName());
        }  
        if (req.getTeam() != null) {
            dev.setTeam(req.getTeam());
        }
        if (!req.getSkills().isEmpty()) {
            dev.setSkills(req.getSkills());
        }
    }

    private Iterable<Developer> findDevelopers(String name, String team, Pageable pageable) {

        Iterable<Developer> results = null;

        if (name != null && team != null){
            results = developerRepository.findByNameAndTeam(name, team, pageable);
        } else if (team != null) {
            results = developerRepository.findByTeam(team, pageable);
        } else if (name != null) {
            results = developerRepository.findByName(name, pageable);
        } else {
            results = developerRepository.findAll(pageable).getContent();
        }       

        return results;
    }
}