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

    public Iterable<Developer> findAll(Pageable pageable) {
        return developerRepository.findAll(pageable).getContent();
    }

    @Transactional
    public Developer updateDeveloper(Developer req, String id) {
        Developer developer = developerRepository.findById(id).orElseThrow(NotFoundException::new);

        Timestamp time = new Timestamp(System.currentTimeMillis());
        if (req.getName() != null) {
            developer.setName(req.getName());
            developer.setUpdatedAt(time.toString());
        }  
        if (req.getTeam() != null) {
            developer.setTeam(req.getTeam());
            developer.setUpdatedAt(time.toString());
        }
        if (req.getSkills().isEmpty()) {
            developer.setSkills(req.getSkills());
            developer.setUpdatedAt(time.toString());
        }

        return developerRepository.update(developer);
    }

    public Developer saveDeveloper(Developer developer) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        developer.setCreatedAt(time.toString());
        developer.setUpdatedAt(time.toString());

        return developerRepository.save(developer);
    }
            
    public void deleteDeveloperById(String id) {
        developerRepository.deleteById(id);
    }
}