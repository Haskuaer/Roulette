package ef.services;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSession
{
    private final UUID sessionId;
    private final List<Socket> players;
    private static final int MAX_PLAYERS = 4;

    public GameSession(){
        this.sessionId = UUID.randomUUID();
        this.players = new ArrayList<>();
    }

    public UUID getSessionId(){ return sessionId; }

    public synchronized boolean isFull(){ return players.size() >= MAX_PLAYERS; }

    public synchronized void addPlayer(Socket player){ players.add(player); }
}
