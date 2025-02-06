package ef.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "game_sessions")
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
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Round> rounds = new HashSet<>();
    @ManyToMany(mappedBy = "sessions")
    private Set<User> users = new HashSet<>();

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

    public Set<Round> getRounds() { return rounds; }
    public void setRounds(Set<Round> rounds) { this.rounds = rounds; }

    public Set<User> getUsers() { return users; }
    public void addUser(User user) { this.users.add(user); }

    public void addRound(Round round) { this.rounds.add(round); }
    public void removeRound(Round round) { this.rounds.remove(round); }
    public boolean isFull(){ return playersCount == maxPlayers; }
}
