package animeta.module;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.hibernate.SessionFactory;

import animeta.dao.RecordDAO;
import animeta.dao.TitleMappingDAO;
import animeta.dao.UserDAO;
import animeta.dao.WorkDAO;

public class DatabaseModule extends AbstractBinder {
    private final SessionFactory sessionFactory;

    public DatabaseModule(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override protected void configure() {
        bind(sessionFactory).to(SessionFactory.class);

        bindAsContract(UserDAO.class).in(Singleton.class);
        bindAsContract(WorkDAO.class).in(Singleton.class);
        bindAsContract(TitleMappingDAO.class).in(Singleton.class);
        bindAsContract(RecordDAO.class).in(Singleton.class);
    }
}
