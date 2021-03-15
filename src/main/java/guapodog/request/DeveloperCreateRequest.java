package guapodog.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class DeveloperCreateRequest {
    
    @NotBlank
    private String id;

    @NotBlank
    private String name;

    private String team;
    
    private List<String> skills;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
