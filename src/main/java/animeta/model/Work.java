package animeta.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "work_work")
public class Work implements Serializable {

    private static final long serialVersionUID = -825186807;

    private Integer id;
    private String  title;
    private String  rawMetadata;
    private String  imageFilename;
    private String  originalImageFilename;

    public Work() {}

    public Work(Work value) {
        this.id = value.id;
        this.title = value.title;
        this.rawMetadata = value.rawMetadata;
        this.imageFilename = value.imageFilename;
        this.originalImageFilename = value.originalImageFilename;
    }

    public Work(
        Integer id,
        String  title,
        String  rawMetadata,
        String  imageFilename,
        String  originalImageFilename
    ) {
        this.id = id;
        this.title = title;
        this.rawMetadata = rawMetadata;
        this.imageFilename = imageFilename;
        this.originalImageFilename = originalImageFilename;
    }

    @Id
    @Column(name = "id", nullable = false, precision = 32)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "title", nullable = false, length = 100)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "raw_metadata")
    public String getRawMetadata() {
        return this.rawMetadata;
    }

    public void setRawMetadata(String rawMetadata) {
        this.rawMetadata = rawMetadata;
    }

    @Column(name = "image_filename", length = 100)
    public String getImageFilename() {
        return this.imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }

    @Column(name = "original_image_filename", length = 100)
    public String getOriginalImageFilename() {
        return this.originalImageFilename;
    }

    public void setOriginalImageFilename(String originalImageFilename) {
        this.originalImageFilename = originalImageFilename;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Work (");

        sb.append(id);
        sb.append(", ").append(title);
        sb.append(", ").append(rawMetadata);
        sb.append(", ").append(imageFilename);
        sb.append(", ").append(originalImageFilename);

        sb.append(")");
        return sb.toString();
    }
}
