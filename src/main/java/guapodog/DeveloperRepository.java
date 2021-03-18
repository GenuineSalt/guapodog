package guapodog;

import guapodog.entity.Developer;
import guapodog.exception.*;
import guapodog.request.DeveloperCreateRequest;
import guapodog.request.DeveloperUpdateRequest;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import io.micronaut.transaction.annotation.ReadOnly;
import org.dozer.DozerBeanMapper;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton 
public class DeveloperRepository implements IDeveloperRepository {
    private final EntityManager entityManager;  
    private final ApplicationConfiguration applicationConfiguration;
    private static DozerBeanMapper mapper;

    public DeveloperRepository(EntityManager entityManager,
                               ApplicationConfiguration applicationConfiguration) { 
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
        mapper = new DozerBeanMapper();
    }

    @Override
    @ReadOnly  
    public Developer findById(String id) throws NotFoundException {
        return Optional.ofNullable(entityManager.find(Developer.class, id)).orElseThrow(NotFoundException::new);
    }

    @ReadOnly 
    public List<Developer> findAll(QueryParameters args) {
        String qlString = "SELECT d FROM Developer as d";

        if (args.getName().isPresent() && args.getTeam().isPresent()) 
            qlString += " WHERE d.name = :name AND d.team = :team";
        else if (args.getName().isPresent())
            qlString += " WHERE d.name = :name";
        else if (args.getTeam().isPresent())
            qlString += " WHERE d.team = :team";

        if (args.getSort().isPresent()) 
            qlString += " ORDER BY d." + args.getSortField() + " " + args.getSortDirection().toLowerCase();

        TypedQuery<Developer> query = entityManager.createQuery(qlString, Developer.class);

        int page = args.getPage().orElseGet(applicationConfiguration::getPage);
        int pageSize = args.getPageSize().orElseGet(applicationConfiguration::getPageSize);
        int offset = (page - 1) * pageSize;

        args.getName().ifPresent(name -> query.setParameter("name", name));
        args.getTeam().ifPresent(team -> query.setParameter("team", team));
        query.setMaxResults(pageSize);
        query.setFirstResult(offset);

        return query.getResultList();
    }

    @Override
    @Transactional  
    public Developer save(DeveloperCreateRequest req) {
        Developer developer = mapper.map(req, Developer.class);

        if (developer.getId() != null) {
            if (entityManager.find(Developer.class, developer.getId()) != null)
                throw new BadRequestException("A developer with id: '" + developer.getId() + "' already exists");
        }
        else {
            String id;
            do {
                id = UUID.randomUUID().toString();
            } while (entityManager.find(Developer.class, id) != null);
            developer.setId(id);
        }

        Timestamp time = new Timestamp(System.currentTimeMillis());
        developer.setCreatedAt(time.toString());
        developer.setUpdatedAt(time.toString());

        entityManager.persist(developer);
        return developer;
    }

    @Override
    @Transactional 
    public Developer update(String id, DeveloperUpdateRequest req) {
        Developer developer = Optional.ofNullable(entityManager.find(Developer.class, id)).orElseThrow(NotFoundException::new);
        
        Timestamp time = new Timestamp(System.currentTimeMillis());
        if (req.getName() != null) {
            developer.setName(req.getName());
            developer.setUpdatedAt(time.toString());
        }  
        if (req.getTeam() != null) {
            developer.setTeam(req.getTeam());
            developer.setUpdatedAt(time.toString());
        }
        if (req.getSkills() != null) {
            developer.setSkills(req.getSkills());
            developer.setUpdatedAt(time.toString());
        }

        entityManager.merge(developer);
        return developer;
    }

    @Override
    @Transactional 
    public void deleteById(String id) {
        Developer developer = entityManager.find(Developer.class, id);
        if (developer != null)
            entityManager.remove(developer);
    }
}