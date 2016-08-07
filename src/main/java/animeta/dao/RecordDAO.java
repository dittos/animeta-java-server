package animeta.dao;

import animeta.model.QRecord;
import animeta.model.Record;
import animeta.model.User;
import animeta.model.Work;

public class RecordDAO extends AbstractDAO<Record> {
    public Record find(int id) {
        return getInternal(id);
    }

    public Record find(User user, Work work) {
        QRecord record = QRecord.record;
        return queryFactory().selectFrom(record)
                .where(record.userId.eq(user.getId()),
                        record.workId.eq(work.getId()))
                .fetchOne();
    }

    public int count(Work work) {
        QRecord record = QRecord.record;
        return (int) queryFactory().selectFrom(record)
                .where(record.workId.eq(work.getId()))
                .fetchCount();
    }
}
