package guapodog;

import java.sql.Timestamp;
<<<<<<< Updated upstream
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
=======
>>>>>>> Stashed changes

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

<<<<<<< Updated upstream
        if (args.getName().isPresent() && args.getTeam().isPresent()) 
            qlString += " WHERE d.name = :name AND d.team = :team";
        else if (args.getName().isPresent())
            qlString += " WHERE d.name = :name";
        else if (args.getTeam().isPresent())
            qlString += " WHERE d.team = :team";
=======
import guapodog.entity.Developer;
import guapodog.exception.NotFoundException;
import io.micronaut.data.model.Pageable;
>>>>>>> Stashed changes

@Singleton 
public class DeveloperRepository {

<<<<<<< Updated upstream
        TypedQuery<Developer> query = entityManager.createQuery(qlString, Developer.class);

        int page = args.getPage().orElseGet(applicationConfiguration::getPage);
        int pageSize = args.getPageSize().orElseGet(applicationConfiguration::getPageSize);
        int offset = (page - 1) * pageSize;

        args.getName().ifPresent(name -> query.setParameter("name", name));
        args.getTeam().ifPresent(team -> query.setParameter("team", team));
        query.setMaxResults(pageSize);
        query.setFirstResult(offset);
=======
    @Inject
    IDeveloperRepository developerRepository;
>>>>>>> Stashed changes

    public Developer findById(Long id) throws NotFoundException {
        //removed Optional, CrudRepo findbyId already is Optional
        return developerRepository.findById(id).orElseThrow(NotFoundException::new);
    }

<<<<<<< Updated upstream
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
=======
    public Iterable<Developer> findAll(Pageable pageable) {
        //using pageable from PageableRepo + configs
        return developerRepository.findAll(pageable);
    }

    @Transactional
    public Developer updateDeveloper(Developer req, Long id) {
>>>>>>> Stashed changes
        
        //removed Optional, CrudRepo findbyId already is Optional
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

<<<<<<< Updated upstream
    @Override
    @Transactional 
    public void deleteById(String id) {
        Developer developer = entityManager.find(Developer.class, id);
        if (developer != null)
            entityManager.remove(developer);
=======
    public Developer saveDeveloper(Developer developer) {
        
        Timestamp time = new Timestamp(System.currentTimeMillis());
        developer.setCreatedAt(time.toString());
        developer.setUpdatedAt(time.toString());

        return developerRepository.save(developer);

    }
            
    public void deleteDeveloperById(Long id) {
        developerRepository.deleteById(id);
>>>>>>> Stashed changes
    }

    // @Override
    // @ReadOnly  
    // public Developer findById(String id) throws NotFoundException {
    //     return Optional.ofNullable(entityManager.find(Developer.class, UUID.fromString(id))).orElseThrow(NotFoundException::new);
    // }



    // @ReadOnly 
    // public List<Developer> findAll(QueryParameters args) {
    //     String qlString = "SELECT d FROM Developer as d";

    //     if (args.getName().isPresent() && args.getTeam().isPresent()) 
    //         qlString += " WHERE d.name = '" + args.getName().get() + "' AND d.team = '" + args.getTeam().get() + "'";
    //     else if (args.getName().isPresent())
    //         qlString += " WHERE d.name = '" + args.getName().get() + "'";
    //     else if (args.getTeam().isPresent())
    //         qlString += " WHERE d.team = '" + args.getTeam().get() + "'";

    //     if (args.getSort().isPresent()) 
    //         qlString += " ORDER BY d." + args.getSortField() + " " + args.getSortDirection().toLowerCase();

    //     TypedQuery<Developer> query = entityManager.createQuery(qlString, Developer.class);
    //     query.setMaxResults(args.getPageSize().orElseGet(applicationConfiguration::getPageSize));
    //     args.getPage().ifPresent(query::setFirstResult);

    //     return query.getResultList();
    // }

    // @Override
    // @Transactional  
    // public Developer save(DeveloperCreateRequest req) {
    //     Developer developer = mapper.map(req, Developer.class);

    //     Timestamp time = new Timestamp(System.currentTimeMillis());
    //     developer.setCreatedAt(time.toString());
    //     developer.setUpdatedAt(time.toString());

    //     entityManager.persist(developer);
    //     return developer;
    // }

    // @Override
    // @Transactional 
    // public Developer update(String id, DeveloperUpdateRequest req) {
    //     Developer developer = Optional.ofNullable(entityManager.find(Developer.class, UUID.fromString(id))).orElseThrow(NotFoundException::new);
        
    //     Timestamp time = new Timestamp(System.currentTimeMillis());
    //     if (req.getName() != null) {
    //         developer.setName(req.getName());
    //         developer.setUpdatedAt(time.toString());
    //     }  
    //     if (req.getTeam() != null) {
    //         developer.setTeam(req.getTeam());
    //         developer.setUpdatedAt(time.toString());
    //     }
    //     if (req.getSkills() != null) {
    //         developer.setSkills(req.getSkills());
    //         developer.setUpdatedAt(time.toString());
    //     }

    //     entityManager.merge(developer);
    //     return developer;
    // }

    // @Override
    // @Transactional 
    // public void deleteById(String id) {
    //     Developer developer = entityManager.find(Developer.class, UUID.fromString(id));
    //     if (developer != null)
    //         entityManager.remove(developer);
    // }
}