package ef.client.services;

import ef.client.util.ClientSocket;
import ef.client.util.ClientSocketHolder;
import ef.client.util.UserID_Holder;

import java.util.UUID;

public class Game extends Service
{
    private ClientSocket clientSocket = ClientSocketHolder.getClientSocket();
    private UUID userId = UserID_Holder.getUserId();
    private String status;

    public Game(String status){ this.status = status; }


}
