package guapodog;

import io.micronaut.core.annotation.Introspected;
import javax.annotation.Nullable;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Optional;

@Introspected
public class QueryParameters {

    @Nullable
    @PositiveOrZero 
    private Integer page;

    @Nullable
    @Positive 
    private Integer pageSize;

    @Nullable
    private String name;

    @Nullable
    private String team;

    @Nullable
    @Pattern(regexp = "(id|name|team):(asc|ASC|desc|DESC)")  
    private String sort;

    public QueryParameters() {

    }

    public Optional<Integer> getPage() {
        return Optional.ofNullable(page);
    }

    public void setPage(@Nullable Integer page) {
        this.page = page;
    }

    public Optional<Integer> getPageSize() {
        return Optional.ofNullable(pageSize);
    }

    public void setPageSize(@Nullable Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public void setName(String name) {
        if (!name.isEmpty())
            this.name = name;
    }

    public Optional<String> getTeam() {
        return Optional.ofNullable(team);
    }

    public void setTeam(String team) {
        if (!team.isEmpty())
            this.team = team;
    }

    public Optional<String> getSort() {
        return Optional.ofNullable(sort);
    }

    public void setSort(@Nullable String sort) {
        this.sort = sort;
    }

    public String getSortField() {
        String str = null;
        if (sort != null) 
            str = sort.split(":")[0];
        return str;
    }

    public String getSortDirection() {
        String str = null;
        if (sort != null) 
            str = sort.split(":")[1];
        return str;
    }
}