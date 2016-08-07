package animeta.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "record_history")
public class RecordHistory implements Serializable {

    private static final long serialVersionUID = -1366716587;

    private Integer   id;
    private Integer   userId;
    private Integer   workId;
    private String    status;
    private Timestamp updatedAt;
    private String    comment;
    private RecordStatusType     statusType;
    private Boolean   containsSpoiler;
    private Integer   recordId;

    public RecordHistory() {}

    public RecordHistory(RecordHistory value) {
        this.id = value.id;
        this.userId = value.userId;
        this.workId = value.workId;
        this.status = value.status;
        this.updatedAt = value.updatedAt;
        this.comment = value.comment;
        this.statusType = value.statusType;
        this.containsSpoiler = value.containsSpoiler;
        this.recordId = value.recordId;
    }

    public RecordHistory(
        Integer   id,
        Integer   userId,
        Integer   workId,
        String    status,
        Timestamp updatedAt,
        String    comment,
        RecordStatusType     statusType,
        Boolean   containsSpoiler,
        Integer   recordId
    ) {
        this.id = id;
        this.userId = userId;
        this.workId = workId;
        this.status = status;
        this.updatedAt = updatedAt;
        this.comment = comment;
        this.statusType = statusType;
        this.containsSpoiler = containsSpoiler;
        this.recordId = recordId;
    }

    @Id
    @Column(name = "id", nullable = false, precision = 32)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "user_id", nullable = false, precision = 32)
    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "work_id", nullable = false, precision = 32)
    public Integer getWorkId() {
        return this.workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    @Column(name = "status", length = 30)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "updated_at")
    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Column(name = "comment", nullable = false)
    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "status_type", nullable = false, precision = 16)
    public RecordStatusType getStatusType() {
        return this.statusType;
    }

    public void setStatusType(RecordStatusType statusType) {
        this.statusType = statusType;
    }

    @Column(name = "contains_spoiler", nullable = false)
    public Boolean getContainsSpoiler() {
        return this.containsSpoiler;
    }

    public void setContainsSpoiler(Boolean containsSpoiler) {
        this.containsSpoiler = containsSpoiler;
    }

    @Column(name = "record_id", nullable = false, precision = 32)
    public Integer getRecordId() {
        return this.recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RecordHistory (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(workId);
        sb.append(", ").append(status);
        sb.append(", ").append(updatedAt);
        sb.append(", ").append(comment);
        sb.append(", ").append(statusType);
        sb.append(", ").append(containsSpoiler);
        sb.append(", ").append(recordId);

        sb.append(")");
        return sb.toString();
    }
}
