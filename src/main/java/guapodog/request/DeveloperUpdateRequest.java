package guapodog.request;

import java.util.List;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class DeveloperUpdateRequest {

    private String name;

    private String team;
    
    private List<String> skills;

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
