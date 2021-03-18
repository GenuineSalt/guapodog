package guapodog.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
<<<<<<< Updated upstream
import java.util.ArrayList;
import java.util.List;
=======
>>>>>>> Stashed changes

@Entity
@Table(name = "developer")
public class Developer {

    public Developer() {}

    public Developer(@NotNull String name) {
        this.name = name;
    }

    @Id
<<<<<<< Updated upstream
    private String id;
=======
    @GeneratedValue
    private Long id;
>>>>>>> Stashed changes

    private String name;

    private String team;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "skill", joinColumns = @JoinColumn(name = "developer_id"))
    @Column(name = "skill")
    private List<String> skills = new ArrayList<String>();

    private String createdAt;

    private String updatedAt;

<<<<<<< Updated upstream
    public String getId() {
        return id;
    }

    public void setId(String id) {
=======
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
>>>>>>> Stashed changes
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
