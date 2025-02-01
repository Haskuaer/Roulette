package ef.client.requests;

import java.util.UUID;

public class AuthRequest {

    private String action;
    private UUID userId;

    public AuthRequest(String action, UUID userId) { this.action = action; this.userId = userId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
}
