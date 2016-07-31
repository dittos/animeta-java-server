package animeta;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import animeta.model.Record;
import animeta.model.RecordHistory;
import animeta.model.TitleMapping;
import animeta.model.User;
import animeta.model.Work;
import animeta.model.WorkIndex;
import animeta.module.DatabaseModule;
import animeta.security.AuthContext;
import animeta.security.AuthContextFactory;
import animeta.security.DjangoAuthSessionFilter;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Animeta extends Application<AnimetaConfiguration> {
    public static void main(String[] args) throws Exception {
        new Animeta().run(args);
    }

    private final HibernateBundle<AnimetaConfiguration> hibernate = new HibernateBundle<AnimetaConfiguration>(User.class, Work.class, TitleMapping.class, Record.class, RecordHistory.class, WorkIndex.class) {
        public PooledDataSourceFactory getDataSourceFactory(AnimetaConfiguration configuration) {
            return configuration.database;
        }
    };

    @Override public void initialize(Bootstrap<AnimetaConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override public void run(AnimetaConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new DjangoAuthSessionFilter(configuration.secretKey));
        environment.jersey().register(new DatabaseModule(hibernate.getSessionFactory()));
        environment.jersey().register(new AbstractBinder() {
            @Override protected void configure() {
                bindFactory(AuthContextFactory.class).to(AuthContext.class);
            }
        });
        environment.jersey().packages("animeta.resource");
    }
}
