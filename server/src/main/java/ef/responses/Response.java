package ef.responses;

import java.util.UUID;

public class Response
{
    private String status;
    private UUID userId;

    public Response(String status, UUID userId){ this.status = status; this.userId = userId; }

    public String getStatus(){ return status; }
    public void setStatus(String status){ this.status = status; }

    public UUID getUserId(){ return userId; }
    public void setUserId(UUID userId){ this.userId = userId; }
}
