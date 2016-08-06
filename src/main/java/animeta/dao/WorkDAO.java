package animeta.dao;

import java.util.List;
import java.util.SortedMap;

import javax.inject.Inject;

import animeta.model.Episode;
import animeta.model.QRecordHistory;
import animeta.model.Work;
import animeta.model.WorkIndex;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import com.querydsl.core.Tuple;

public class WorkDAO extends AbstractDAO<Work> {
    private final TitleMappingDAO titleMappingDAO;

    @Inject
    public WorkDAO(TitleMappingDAO titleMappingDAO) {
        this.titleMappingDAO = titleMappingDAO;
    }

    public Work find(int id) {
        return get(id);
    }

    public Work findByTitle(String title) {
        return get(titleMappingDAO.find(title).getWorkId());
    }

    public List<Episode> findEpisodes(Work work) {
        SortedMap<Integer, Episode> episodeMap = Maps.newTreeMap();
        List<Tuple> statusesWithComment = queryFactory()
                .select(QRecordHistory.recordHistory.status, QRecordHistory.recordHistory.status.count())
                .from(QRecordHistory.recordHistory)
                .where(QRecordHistory.recordHistory.workId.eq(work.getId()),
                        QRecordHistory.recordHistory.comment.ne(""))
                .groupBy(QRecordHistory.recordHistory.status)
                .fetch();
        for (Tuple row : statusesWithComment) {
            Integer number = Ints.tryParse(row.get(QRecordHistory.recordHistory.status));
            if (number == null) {
                continue;
            }
            episodeMap.put(number, new Episode(number, row.get(QRecordHistory.recordHistory.status.count()).intValue()));
        }
        List<Tuple> statusesWithoutComment = queryFactory()
                .select(QRecordHistory.recordHistory.status, QRecordHistory.recordHistory.status.count())
                .from(QRecordHistory.recordHistory)
                .where(QRecordHistory.recordHistory.workId.eq(work.getId()),
                        QRecordHistory.recordHistory.comment.eq(""))
                .groupBy(QRecordHistory.recordHistory.status)
                .fetch();
        for (Tuple row : statusesWithoutComment) {
            Integer number = Ints.tryParse(row.get(QRecordHistory.recordHistory.status));
            if (number == null) {
                continue;
            }
            if (episodeMap.containsKey(number)) {
                continue;
            }
            if (row.get(QRecordHistory.recordHistory.status.count()) <= 1) {
                continue;
            }
            episodeMap.put(number, new Episode(number));
        }
        return Lists.newArrayList(episodeMap.values());
    }

    public WorkIndex findIndex(Work work) {
        return currentSession().get(WorkIndex.class, work.getId());
    }
}
