package animeta.dao;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import animeta.model.TitleMapping;
import io.dropwizard.hibernate.AbstractDAO;

public class TitleMappingDAO extends AbstractDAO<TitleMapping> {
    @Inject
    public TitleMappingDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public TitleMapping find(String title) {
        return uniqueResult(criteria().add(Restrictions.eq("title", title)));
    }
}
