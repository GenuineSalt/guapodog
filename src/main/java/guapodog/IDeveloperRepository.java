package guapodog;

import guapodog.domain.Developer;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface IDeveloperRepository {

    Developer findById(@NotNull String id);

    List<Developer> findAll(@NotNull SortingAndOrderArguments args);

    Developer save(@Valid Developer developer);

    Developer update(@Valid Developer developer);

    void deleteById(@NotNull String id);
}