package animeta.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Schedule {
    @JsonProperty("date_only")
    public Boolean dateOnly;
    public long date;
    public List<String> broadcasts;
}
