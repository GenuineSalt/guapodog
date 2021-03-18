package guapodog;

import java.net.URI;

import javax.inject.Inject;
import javax.validation.Valid;

import guapodog.entity.Developer;
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

<<<<<<< Updated upstream
    @Get("/{?args*}")
=======
    @Get("/")
>>>>>>> Stashed changes
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Iterable<Developer>> getDevelopers(Pageable pageable) {
        return HttpResponse.ok(developerRepository.findAll(pageable));
    }

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public MutableHttpResponse<Developer> getDeveloper(Long id) {
        return HttpResponse.ok(developerRepository.findById(id));
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Developer> createDeveloper(@Body @Valid Developer req) {
        Developer developer = developerRepository.saveDeveloper(req);
        return HttpResponse.created(developer).headers(headers -> headers.location(location(developer)));
    }

    @Patch("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Developer> updateDeveloper(Long id, @Body @Valid Developer req) { 
        Developer developer = developerRepository.updateDeveloper(req, id);
        return HttpResponse.ok(developer).headers(headers -> headers.location(location(developer)));
    }

    @Delete("/{id}") 
    public HttpResponse delete(Long id) {
        developerRepository.deleteDeveloperById(id);
        return HttpResponse.noContent();
    }


    // Helpers

    private URI location(Developer developer) {
        return URI.create("/developers/" + developer.getId());
    }
}
