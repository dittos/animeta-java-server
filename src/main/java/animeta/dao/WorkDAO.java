package animeta.dao;

import java.util.List;
import java.util.SortedMap;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import animeta.model.Episode;
import animeta.model.RecordHistory;
import animeta.model.Work;
import animeta.model.WorkIndex;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import io.dropwizard.hibernate.AbstractDAO;

public class WorkDAO extends AbstractDAO<Work> {
    private final TitleMappingDAO titleMappingDAO;

    @Inject
    public WorkDAO(SessionFactory sessionFactory, TitleMappingDAO titleMappingDAO) {
        super(sessionFactory);
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
        List<Object[]> statusesWithComment = currentSession().createCriteria(RecordHistory.class)
                .add(Restrictions.eq("workId", work.getId()))
                .add(Restrictions.ne("comment", ""))
                .setProjection(Projections.projectionList()
                        .add(Projections.groupProperty("status"))
                        .add(Projections.rowCount()))
                .list();
        for (Object[] row : statusesWithComment) {
            Integer number = Ints.tryParse((String) row[0]);
            if (number == null) {
                continue;
            }
            episodeMap.put(number, new Episode(number, ((Long) row[1]).intValue()));
        }
        List<Object[]> statusesWithoutComment = currentSession().createCriteria(RecordHistory.class)
                .add(Restrictions.eq("workId", work.getId()))
                .add(Restrictions.eq("comment", ""))
                .setProjection(Projections.projectionList()
                        .add(Projections.groupProperty("status"))
                        .add(Projections.rowCount()))
                .list();
        for (Object[] row : statusesWithoutComment) {
            Integer number = Ints.tryParse((String) row[0]);
            if (number == null) {
                continue;
            }
            if (episodeMap.containsKey(number)) {
                continue;
            }
            if ((Long) row[1] <= 1) {
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
