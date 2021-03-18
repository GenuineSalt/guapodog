package guapodog;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("application") 
public class ApplicationConfigurationProperties implements ApplicationConfiguration {

    @NonNull
    private Integer page;

    @NonNull
    private Integer pageSize;

    @Override
    @NonNull
    public Integer getPage() {
        return page;
    }

    public void setPage(@NonNull Integer page) {
        if(page != null) {
            this.page = page;
        }
    }

    @Override
    @NonNull
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(@NonNull Integer pageSize) {
        if(pageSize != null) {
            this.pageSize = pageSize;
        }
    }
}