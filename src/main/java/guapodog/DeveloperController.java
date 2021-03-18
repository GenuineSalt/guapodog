package guapodog;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import guapodog.entity.*;
import guapodog.request.*;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@ExecuteOn(TaskExecutors.IO)
@Controller("/developers")
public class DeveloperController {
    protected final DeveloperRepository developerRepository;

    public DeveloperController(DeveloperRepository developerRepository) { 
        this.developerRepository = developerRepository;
    }

    @Get("/{?args*}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<List<Developer>> GetDevelopers(@Valid QueryParameters args) {
        return HttpResponse.ok(developerRepository.findAll(args));
    }

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Developer> GetDeveloper(String id) {
        return HttpResponse.ok(developerRepository.findById(id));
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Developer> CreateDeveloper(@Body @Valid DeveloperCreateRequest req) {
        Developer developer = developerRepository.save(req);
        return HttpResponse.created(developer).headers(headers -> headers.location(location(developer)));
    }

    @Patch("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Developer> UpdateDeveloper(String id, @Body @Valid DeveloperUpdateRequest req) { 
        return HttpResponse.ok(developerRepository.update(id, req));  
    }

    @Delete("/{id}") 
    public HttpResponse delete(String id) {
        developerRepository.deleteById(id);
        return HttpResponse.noContent();
    }


    // Helpers

    private URI location(Developer developer) {
        return URI.create("/developers/" + developer.getId());
    }
}
