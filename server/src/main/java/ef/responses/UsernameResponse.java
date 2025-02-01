package ef.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UsernameResponse extends Response {

    private String username;

    @JsonCreator
    public UsernameResponse(
            @JsonProperty("status") String status,
            @JsonProperty("userId") UUID userId,
            @JsonProperty("username") String username )
    {
        super(status, userId);
        this.username = username;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
