package guapodog;

import java.net.URI;
import javax.inject.Inject;
import javax.validation.Valid;
import guapodog.entity.Developer;
import guapodog.exception.BadRequestException;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@ExecuteOn(TaskExecutors.IO)
@Controller("/developers")
public class DeveloperController {

    @Inject
    DeveloperRepository developerRepository;

    public DeveloperController(DeveloperRepository developerRepository) { 
        this.developerRepository = developerRepository;
    }

    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Iterable<Developer>> getDevelopers(Pageable pageable, 
        @Nullable @QueryValue String name, 
        @Nullable @QueryValue String team) {
        return HttpResponse.ok(developerRepository.findAll(pageable, name, team));
    }

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public MutableHttpResponse<Developer> getDeveloper(String id) {
        return HttpResponse.ok(developerRepository.findById(id));
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Developer> createDeveloper(@Body @Valid Developer req) {
        if (req.getName() == null || req.getName().isEmpty())
            throw new BadRequestException("Name must not be empty");
        Developer developer = developerRepository.saveDeveloper(req);
        return HttpResponse.created(developer).headers(headers -> headers.location(location(developer)));
    }

    @Patch("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Developer> updateDeveloper(String id, @Body @Valid Developer req) { 
        Developer developer = developerRepository.updateDeveloper(req, id);
        return HttpResponse.ok(developer).headers(headers -> headers.location(location(developer)));
    }

    @Delete("/{id}") 
    public HttpResponse delete(String id) {
        developerRepository.deleteDeveloperById(id);
        return HttpResponse.noContent();
    }

    // Helpers

    private URI location(Developer developer) {
        return URI.create("/developers/" + developer.getId());
    }
}
