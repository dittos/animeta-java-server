package animeta;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.spi.AbstractContainerLifecycleListener;
import org.glassfish.jersey.server.spi.Container;

import animeta.module.DatabaseModule;
import animeta.module.SerializerModule;
import animeta.security.AuthContext;
import animeta.security.AuthContextFactory;
import animeta.security.DjangoAuthSessionFilter;
import animeta.util.JsonParamConverterProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Animeta extends Application<AnimetaConfiguration> {
    public static void main(String[] args) throws Exception {
        new Animeta().run(args);
    }

    private ServiceLocator serviceLocator;

    private final HibernateBundle<AnimetaConfiguration> hibernate = new ScanningHibernateBundle<AnimetaConfiguration>("animeta.model") {
        public PooledDataSourceFactory getDataSourceFactory(AnimetaConfiguration configuration) {
            return configuration.database;
        }
    };

    @Override public void initialize(Bootstrap<AnimetaConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override public void run(AnimetaConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new AbstractContainerLifecycleListener() {
            @Override public void onStartup(Container container) {
                serviceLocator = container.getApplicationHandler().getServiceLocator();
            }
        });
        environment.jersey().register(JsonParamConverterProvider.class);
        environment.jersey().register(new DjangoAuthSessionFilter(configuration.secretKey));
        environment.jersey().register(new DatabaseModule(hibernate.getSessionFactory()));
        environment.jersey().register(new SerializerModule());
        environment.jersey().register(new AbstractBinder() {
            @Override protected void configure() {
                bindFactory(AuthContextFactory.class).to(AuthContext.class);
                bind(environment.getObjectMapper()).to(ObjectMapper.class);
            }
        });
        environment.jersey().packages("animeta.resource");
    }

    public ServiceLocator getServiceLocator() {
        return serviceLocator;
    }
}
