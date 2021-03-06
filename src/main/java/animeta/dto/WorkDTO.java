package animeta.dto;

import java.util.List;

import animeta.model.Episode;
import animeta.model.Record;
import animeta.model.Work;
import animeta.model.WorkMetadata;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkDTO {
    private final Work work;
    private List<Episode> episodes;
    private int recordCount;
    private Integer rank;
    private Record record;
    private WorkMetadata metadata;

    public WorkDTO(Work work) {
        this.work = work;
    }

    public int getId() {
        return work.getId();
    }

    public String getTitle() {
        return work.getTitle();
    }

    public String getImageUrl() {
        return "https://animeta.net/media/" + work.getImageFilename();
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public WorkMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(WorkMetadata metadata) {
        this.metadata = metadata;
    }
}
