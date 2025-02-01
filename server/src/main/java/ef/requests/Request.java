package ef.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Request {

    private String action;

    public Request(
            @JsonProperty("action") String action )
    {
        this.action = action;
    }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
}