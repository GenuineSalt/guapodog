package guapodog;

import java.util.List;
import javax.validation.Valid;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;
import edu.umd.cs.findbugs.annotations.Nullable;
import guapodog.domain.*;
import guapodog.entity.*;
import guapodog.exception.*;
import guapodog.request.*;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

@Controller("/developers")
public class DeveloperController {
    protected final DeveloperRepository developerRepository;
    private static DozerBeanMapper mapper;

    public DeveloperController(DeveloperRepository developerRepository) { 
        this.developerRepository = developerRepository;
        mapper = new DozerBeanMapper();
    }

    @Get("/list{?args*}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<List<Developer>> GetDevelopers(@Valid SortingAndOrderArguments args) {
        return HttpResponse.ok(developerRepository.findAll(args));
    }

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Developer> GetDeveloper(String id) {
        return HttpResponse.ok(developerRepository.findById(id));
    }

    @Post 
    public HttpResponse<Developer> CreateDeveloper(@Body @Valid DeveloperCreateRequest req) {

        // ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        // String json = ow.writeValueAsString(req);
        // System.out.println(json);

        // Developer domain = mapper.map(req, Developer.class);
        
        // json = ow.writeValueAsString(domain);
        // System.out.println(json);

        Developer developer = developerRepository.save(mapper.map(req, Developer.class));

        return HttpResponse
                .created(developer);
                // .headers(headers -> headers.location(location(req.getId())));
    }

    @Patch("/{id}")
    public HttpResponse UpdateDeveloper(String id, @Body @Valid DeveloperUpdateRequest req) { 
        if (!id.equals(req.getId()))
            throw new BadRequestException("Path parameter id does not match json payload id");

        Developer existingDeveloper = developerRepository.findById(id);

        Developer developer = developerRepository.update(patch(existingDeveloper, req));

        return HttpResponse
                .ok(developer);
                // .header(HttpHeaders.LOCATION, location(command.getId()).getPath()); 
    }

    @Delete("/{id}") 
    public HttpResponse delete(String id) {
        developerRepository.deleteById(id);
        return HttpResponse.noContent();
    }



    // Helpers
    
    private Developer patch(Developer developer, DeveloperUpdateRequest req) {
        if (req.getName() != null)
            developer.setName(req.getName());
        if (req.getTeam() != null)
            developer.setTeam(req.getTeam());
        if (req.getSkills() != null)
            developer.setSkills(req.getSkills());

        return developer;
    }
}
