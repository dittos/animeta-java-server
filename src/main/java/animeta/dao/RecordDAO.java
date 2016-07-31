package animeta.dao;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import animeta.model.Record;
import animeta.model.User;
import animeta.model.Work;
import io.dropwizard.hibernate.AbstractDAO;

public class RecordDAO extends AbstractDAO<Record> {
    @Inject
    public RecordDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Record find(int id) {
        return get(id);
    }

    public Record find(User user, Work work) {
        return uniqueResult(criteria()
                .add(Restrictions.eq("userId", user.getId()))
                .add(Restrictions.eq("workId", work.getId())));
    }

    public int count(Work work) {
        return (Integer) criteria()
                .add(Restrictions.eq("workId", work.getId()))
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }
}
