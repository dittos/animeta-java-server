package animeta.serializer;

import javax.inject.Inject;

import animeta.dao.RecordDAO;
import animeta.dao.WorkDAO;
import animeta.dto.WorkDTO;
import animeta.dto.WorkFields;
import animeta.model.Work;
import animeta.model.WorkIndex;
import animeta.security.AuthContext;
import animeta.util.WorkMetadatas;

public class WorkSerializer {
    private final WorkDAO workDAO;
    private final RecordDAO recordDAO;

    @Inject
    public WorkSerializer(WorkDAO workDAO, RecordDAO recordDAO) {
        this.workDAO = workDAO;
        this.recordDAO = recordDAO;
    }

    public WorkDTO serialize(Work work, WorkFields fields, AuthContext authContext) {
        WorkDTO dto = new WorkDTO(work);
        dto.setEpisodes(workDAO.getEpisodes(work));
        WorkIndex index = workDAO.getIndex(work);
        if (index != null) {
            dto.setRecordCount(index.getRecordCount());
            dto.setRank(index.getRank());
        } else {
            dto.setRecordCount(recordDAO.count(work));
        }
        if (fields.record && authContext.isAuthenticated()) {
            dto.setRecord(recordDAO.find(authContext.getUser(), work));
        }
        if (fields.metadata) {
            dto.setMetadata(WorkMetadatas.extract(work));
        }
        return dto;
    }
}
