package animeta.dao;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import animeta.model.User;
import io.dropwizard.hibernate.AbstractDAO;

public class UserDAO extends AbstractDAO<User> {
    @Inject
    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User find(int id) {
        return get(id);
    }

    public User find(String username) {
        return uniqueResult(criteria().add(Restrictions.eq("username", username)));
    }
}
