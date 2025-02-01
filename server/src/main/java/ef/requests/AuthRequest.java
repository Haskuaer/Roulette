package ef.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class AuthRequest extends Request{

    private String action;
    private UUID userId;

    public AuthRequest(
            @JsonProperty("action") String action,
            @JsonProperty("userId") UUID userId )
    {
        super(action);
        this.userId = userId;
    }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
}