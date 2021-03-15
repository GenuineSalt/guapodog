package guapodog;

import guapodog.domain.Developer;
import guapodog.entity.DeveloperEntity;
import guapodog.exception.*;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        DeveloperEntity developerEntity = entityManager.find(DeveloperEntity.class, id);

        if (developerEntity == null)
            throw new NotFoundException();

        return mapper.map(developerEntity, Developer.class);
    }

    private final static List<String> VALID_PROPERTY_NAMES = Arrays.asList("id", "name");

    @ReadOnly 
    public List<Developer> findAll(SortingAndOrderArguments args) {
        String qlString = "SELECT d FROM DeveloperEntity as d";
        if (args.getOrder().isPresent() && args.getSort().isPresent() && VALID_PROPERTY_NAMES.contains(args.getSort().get())) {
                qlString += " ORDER BY d." + args.getSort().get() + " " + args.getOrder().get().toLowerCase();
        }
        TypedQuery<DeveloperEntity> query = entityManager.createQuery(qlString, DeveloperEntity.class);
        query.setMaxResults(args.getMax().orElseGet(applicationConfiguration::getMax));
        args.getOffset().ifPresent(query::setFirstResult);

        List<DeveloperEntity> developerEntities = query.getResultList();
        List<Developer> developers = new ArrayList<Developer>();

        for (DeveloperEntity developerEntity : developerEntities) {
            developers.add(mapper.map(developerEntity, Developer.class));
        }

        return developers;
    }

    @Override
    @Transactional  
    public Developer save(Developer developer) {
        DeveloperEntity developerEntity = mapper.map(developer, DeveloperEntity.class);
        entityManager.persist(developerEntity);
        return developer;
    }

    @Override
    @Transactional 
    public Developer update(Developer developer) {
        DeveloperEntity developerEntity = mapper.map(developer, DeveloperEntity.class);
        entityManager.merge(developerEntity);
        return developer;
    }

    @Override
    @Transactional 
    public void deleteById(String id) {
        DeveloperEntity entity = entityManager.find(DeveloperEntity.class, id);
        if (entity != null)
            entityManager.remove(entity);
    }
}