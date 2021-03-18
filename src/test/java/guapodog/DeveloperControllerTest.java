package guapodog;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import guapodog.entity.Developer;
import guapodog.request.DeveloperCreateRequest;
import guapodog.request.DeveloperUpdateRequest;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest 
public class DeveloperControllerTest {

    @Inject
    @Client("/")
    HttpClient client; 

    @Test
    public void invalidSortParameterReturnsBadRequest() {
        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(HttpRequest.GET("/developers?sort=foo"));
        });

        assertNotNull(thrown.getResponse());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
    }

    @Test
    public void getNonExistentDeveloperReturnsNotFound() {
        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(HttpRequest.GET("/developers/banana123"));
        });

        assertNotNull(thrown.getResponse());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    public void developerIsPersisted() {
        HttpRequest request = HttpRequest.POST("/developers", getBob()); 
        HttpResponse response = client.toBlocking().exchange(request);
        String bobId = entityId(response);
        
        assertEquals(HttpStatus.CREATED, response.getStatus());

        request = HttpRequest.GET("/developers/" + bobId);
        Developer developer = client.toBlocking().retrieve(request, Developer.class);

        assertEquals("Bob", developer.getName());

        cleanUp(bobId);
    }

    @Test
    public void developerIsUpdated() {
        HttpRequest request = HttpRequest.POST("/developers", getBrad()); 
        HttpResponse response = client.toBlocking().exchange(request);
        String bradId = entityId(response);

        assertEquals(HttpStatus.CREATED, response.getStatus());

        DeveloperUpdateRequest update = new DeveloperUpdateRequest() {{ setTeam("Pepega Clap"); }};

        request = HttpRequest.PATCH("/developers/" + bradId, update);
        response = client.toBlocking().exchange(request);

        assertEquals(HttpStatus.OK, response.getStatus());

        request = HttpRequest.GET("/developers/" + bradId);
        Developer developer = client.toBlocking().retrieve(request, Developer.class);

        assertEquals("Pepega Clap", developer.getTeam());

        cleanUp(bradId);
    }

    @Test
    public void developerIsDeleted() {
        HttpRequest request = HttpRequest.POST("/developers", getBen()); 
        HttpResponse response = client.toBlocking().exchange(request);
        String benId = entityId(response);

        assertEquals(HttpStatus.CREATED, response.getStatus());

        request = HttpRequest.DELETE("/developers/" + benId);
        response = client.toBlocking().exchange(request);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());

        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(HttpRequest.GET("/developers/" + benId));
        });

        assertNotNull(thrown.getResponse());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    public void filterListByQueryParameter() {
        HttpRequest request = HttpRequest.POST("/developers", getBob());
        HttpResponse response = client.toBlocking().exchange(request);

        request = HttpRequest.POST("/developers", getBrad());
        response = client.toBlocking().exchange(request);

        request = HttpRequest.POST("/developers", getBen());
        response = client.toBlocking().exchange(request);

        request = HttpRequest.GET("/developers");
        List<Developer> developers = client.toBlocking().retrieve(request, Argument.of(List.class, Developer.class));

        for (Developer d : developers) {
            System.out.println(d.getName());
        }

        assertEquals(3, developers.size());

        request = HttpRequest.GET("/developers?team=Potato");
        developers = client.toBlocking().retrieve(request, Argument.of(List.class, Developer.class));

        assertEquals(2, developers.size());

        request = HttpRequest.GET("/developers?name=Brad");
        developers = client.toBlocking().retrieve(request, Argument.of(List.class, Developer.class));

        assertEquals(1, developers.size());

        request = HttpRequest.GET("/developers?page=2&pageSize=3");
        developers = client.toBlocking().retrieve(request, Argument.of(List.class, Developer.class));

        assertEquals(0, developers.size());
    }

    protected String entityId(HttpResponse response) {
        String path = "/developers/";
        String value = response.header(HttpHeaders.LOCATION);
        if (value == null) {
            return null;
        }
        int index = value.indexOf(path);
        if (index != -1) {
            return value.substring(index + path.length());
        }
        return null;
    }

    protected DeveloperCreateRequest getBob() {
        return new DeveloperCreateRequest() {{
            setName("Bob");
            setTeam("Banana");
            setSkills(Arrays.asList("spending money", "growing beans", "wii golf"));
        }};
    }

    protected DeveloperCreateRequest getBrad() {
        return new DeveloperCreateRequest() {{
            setName("Brad");
            setTeam("Potato");
            setSkills(Arrays.asList("vaping", "flexing"));
        }};
    }

    protected DeveloperCreateRequest getBen() {
        return new DeveloperCreateRequest() {{
            setName("Ben");
            setTeam("Potato");
            setSkills(Arrays.asList("art of persuasion", "watching tv", "walking", "using chopsticks"));
        }};
    }

    protected void cleanUp(List<String> ids) {
        for (String id : ids) {
            cleanUp(id);
        }
    }

    protected void cleanUp(String id) {
        HttpRequest request = HttpRequest.DELETE("/developers/" + id);
        client.toBlocking().exchange(request);
    }
}