package animeta.dao;

import animeta.model.QRecord;
import animeta.model.Record;
import animeta.model.User;
import animeta.model.Work;

public class RecordDAO extends AbstractDAO<Record> {
    public Record find(int id) {
        return get(id);
    }

    public Record find(User user, Work work) {
        return queryFactory().selectFrom(QRecord.record)
                .where(QRecord.record.userId.eq(user.getId()),
                        QRecord.record.workId.eq(work.getId()))
                .fetchOne();
    }

    public int count(Work work) {
        return (int) queryFactory().selectFrom(QRecord.record)
                .where(QRecord.record.workId.eq(work.getId()))
                .fetchCount();
    }
}
