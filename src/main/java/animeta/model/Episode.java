package animeta.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Episode {
    private Integer number;
    private Integer postCount;

    public Episode(Integer number) {
        this.number = number;
    }

    public Episode(Integer number, Integer postCount) {
        this.number = number;
        this.postCount = postCount;
    }

    public Integer getNumber() {
        return number;
    }

    @JsonProperty("post_count")
    public Integer getPostCount() {
        return postCount;
    }
}
