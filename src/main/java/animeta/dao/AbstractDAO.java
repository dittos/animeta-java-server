package animeta.dao;

import java.io.Serializable;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import io.dropwizard.util.Generics;

public abstract class AbstractDAO<E> {
    private SessionFactory sessionFactory;
    private final Class<?> entityClass;

    public AbstractDAO() {
        this.entityClass = Generics.getTypeParameter(getClass());
    }

    @Inject
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected HibernateQueryFactory queryFactory() {
        return new HibernateQueryFactory(currentSession());
    }

    protected E get(Serializable id) {
        return (E) currentSession().get(entityClass, id);
    }
}
