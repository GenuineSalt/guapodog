package guapodog;

import javax.validation.constraints.NotNull;

public interface ApplicationConfiguration {

    @NotNull Integer getPage();

    @NotNull Integer getPageSize();
}