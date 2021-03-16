package guapodog;

import guapodog.entity.Developer;
import guapodog.request.DeveloperCreateRequest;
import guapodog.request.DeveloperUpdateRequest;
import java.util.List;

public interface IDeveloperRepository {

    Developer findById(String id);

    List<Developer> findAll(QueryParameters args);

    Developer save(DeveloperCreateRequest developer);

    Developer update(String id, DeveloperUpdateRequest request);

    void deleteById(String id);
}