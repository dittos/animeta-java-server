package animeta.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "search_workindex")
public class WorkIndex implements Serializable {

    private static final long serialVersionUID = -823551029;

    private Integer workId;
    private String  title;
    private Integer recordCount;
    private Integer rank;

    public WorkIndex() {}

    public WorkIndex(WorkIndex value) {
        this.workId = value.workId;
        this.title = value.title;
        this.recordCount = value.recordCount;
        this.rank = value.rank;
    }

    public WorkIndex(
        Integer workId,
        String  title,
        Integer recordCount,
        Integer rank
    ) {
        this.workId = workId;
        this.title = title;
        this.recordCount = recordCount;
        this.rank = rank;
    }

    @Id
    @Column(name = "work_id", nullable = false, precision = 32)
    public Integer getWorkId() {
        return this.workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    @Column(name = "title", nullable = false, length = 100)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "record_count", nullable = false, precision = 32)
    public Integer getRecordCount() {
        return this.recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

    @Column(name = "rank", nullable = false, precision = 32)
    public Integer getRank() {
        return this.rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("WorkIndex (");

        sb.append(workId);
        sb.append(", ").append(title);
        sb.append(", ").append(recordCount);
        sb.append(", ").append(rank);

        sb.append(")");
        return sb.toString();
    }
}
