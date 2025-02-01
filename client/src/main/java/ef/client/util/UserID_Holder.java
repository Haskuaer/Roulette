package ef.client.util;

import java.util.UUID;

//singleton structure for retriving clientsocket
public class UserID_Holder {

    private static UUID userId;

    public static UUID getUserId() { return userId; }
    public static void setUserId(UUID userId) { UserID_Holder.userId = userId; }
}
