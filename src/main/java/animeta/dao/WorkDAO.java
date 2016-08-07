package animeta.dao;

import java.util.List;
import java.util.SortedMap;

import javax.inject.Inject;

import animeta.model.Episode;
import animeta.model.QRecordHistory;
import animeta.model.TitleMapping;
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

    public Work get(int id) {
        return getInternal(id);
    }

    public Work getByTitle(String title) {
        TitleMapping mapping = titleMappingDAO.get(title);
        if (mapping == null) {
            return null;
        }
        return getInternal(mapping.getWorkId());
    }

    public List<Episode> getEpisodes(Work work) {
        QRecordHistory recordHistory = QRecordHistory.recordHistory;
        SortedMap<Integer, Episode> episodeMap = Maps.newTreeMap();
        List<Tuple> statusesWithComment = queryFactory()
                .select(recordHistory.status, recordHistory.status.count())
                .from(recordHistory)
                .where(recordHistory.workId.eq(work.getId()),
                        recordHistory.comment.ne(""))
                .groupBy(recordHistory.status)
                .fetch();
        for (Tuple row : statusesWithComment) {
            Integer number = Ints.tryParse(row.get(recordHistory.status));
            if (number == null) {
                continue;
            }
            episodeMap.put(number, new Episode(number, row.get(recordHistory.status.count()).intValue()));
        }
        List<Tuple> statusesWithoutComment = queryFactory()
                .select(recordHistory.status, recordHistory.status.count())
                .from(recordHistory)
                .where(recordHistory.workId.eq(work.getId()),
                        recordHistory.comment.eq(""))
                .groupBy(recordHistory.status)
                .fetch();
        for (Tuple row : statusesWithoutComment) {
            Integer number = Ints.tryParse(row.get(recordHistory.status));
            if (number == null) {
                continue;
            }
            if (episodeMap.containsKey(number)) {
                continue;
            }
            if (row.get(recordHistory.status.count()) <= 1) {
                continue;
            }
            episodeMap.put(number, new Episode(number));
        }
        return Lists.newArrayList(episodeMap.values());
    }

    public WorkIndex getIndex(Work work) {
        return currentSession().get(WorkIndex.class, work.getId());
    }
}
