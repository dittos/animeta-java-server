package animeta.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "record_record", schema = "public")
public class Record implements Serializable {

    private static final long serialVersionUID = 305536496;

    private Integer   id;
    private Integer   userId;
    private Integer   workId;
    private String    status;
    private Timestamp updatedAt;
    private Integer   categoryId;
    private RecordStatusType     statusType;
    private String    title;

    public Record() {}

    public Record(Record value) {
        this.id = value.id;
        this.userId = value.userId;
        this.workId = value.workId;
        this.status = value.status;
        this.updatedAt = value.updatedAt;
        this.categoryId = value.categoryId;
        this.statusType = value.statusType;
        this.title = value.title;
    }

    public Record(
        Integer   id,
        Integer   userId,
        Integer   workId,
        String    status,
        Timestamp updatedAt,
        Integer   categoryId,
        RecordStatusType     statusType,
        String    title
    ) {
        this.id = id;
        this.userId = userId;
        this.workId = workId;
        this.status = status;
        this.updatedAt = updatedAt;
        this.categoryId = categoryId;
        this.statusType = statusType;
        this.title = title;
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

    @Column(name = "category_id", precision = 32)
    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Column(name = "status_type", nullable = false, precision = 16)
    public RecordStatusType getStatusType() {
        return this.statusType;
    }

    public void setStatusType(RecordStatusType statusType) {
        this.statusType = statusType;
    }

    @Column(name = "title", nullable = false, length = 100)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Record (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(workId);
        sb.append(", ").append(status);
        sb.append(", ").append(updatedAt);
        sb.append(", ").append(categoryId);
        sb.append(", ").append(statusType);
        sb.append(", ").append(title);

        sb.append(")");
        return sb.toString();
    }
}
