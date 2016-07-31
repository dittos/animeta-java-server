package animeta.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkMetadataLinks {
    public String website;
    public String namu;
    public String ann;
}
