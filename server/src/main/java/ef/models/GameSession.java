package ef.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class GameSession
{
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "status")
    private String status = "waiting";  //waiting, active, finished
    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "players_count")
    private int playersCount = 0;
    @Column(name = "max_players")
    private int maxPlayers = 4;


    public GameSession(){ this.id = UUID.randomUUID(); }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public int getPlayersCount() { return playersCount; }
    public void setPlayersCount(int players_count) { this.playersCount = players_count; }

    public int getMaxPlayers() { return maxPlayers; }
    public void setMaxPlayers(int max_players) { this.maxPlayers = max_players; }

    public boolean isFull(){ return playersCount == maxPlayers; }
}
