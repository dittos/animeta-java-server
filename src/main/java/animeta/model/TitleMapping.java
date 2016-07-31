package animeta.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "work_titlemapping")
public class TitleMapping implements Serializable {

    private static final long serialVersionUID = -172810294;

    private Integer id;
    private Integer workId;
    private String  title;
    private String  key;

    public TitleMapping() {}

    public TitleMapping(TitleMapping value) {
        this.id = value.id;
        this.workId = value.workId;
        this.title = value.title;
        this.key = value.key;
    }

    public TitleMapping(
        Integer id,
        Integer workId,
        String  title,
        String  key
    ) {
        this.id = id;
        this.workId = workId;
        this.title = title;
        this.key = key;
    }

    @Id
    @Column(name = "id", nullable = false, precision = 32)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    @Column(name = "key", nullable = false, length = 100)
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TitleMapping (");

        sb.append(id);
        sb.append(", ").append(workId);
        sb.append(", ").append(title);
        sb.append(", ").append(key);

        sb.append(")");
        return sb.toString();
    }
}
