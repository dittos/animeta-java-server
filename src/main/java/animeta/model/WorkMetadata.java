package animeta.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkMetadata {
    public List<Period> periods;
    public String title;
    public WorkMetadataLinks links = new WorkMetadataLinks();
    public List<String> studios;
    public SourceMaterialType source;
    public Map<String, Schedule> schedule;
}
