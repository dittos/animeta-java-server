package animeta;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class AnimetaConfiguration extends Configuration {
    @NotNull
    @Valid
    public DataSourceFactory database = new DataSourceFactory();

    @NotNull
    public String secretKey;
}
