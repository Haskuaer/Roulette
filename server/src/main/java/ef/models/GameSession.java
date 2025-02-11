package ef.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "game_sessions")
public class GameSession
{
    //ID
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    //SESSION STATUS
    @Column(name = "status")
    private String status;  //waiting, active, finished

    //SESSION CREATION TIME
    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    //PLAYERS COUNT
    @Column(name = "players_count")
    private int playersCount = 0;

    //MAX PLAYERS PER SESSION
    @Column(name = "max_players")
    private int maxPlayers = 3;

    //SESSION USERS
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> users = new HashSet<>();

    //SESSION ROUNDS
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Round> rounds = new HashSet<>();

    //CONSTRUCTORS
    public GameSession(){ this.id = UUID.randomUUID(); this.status = "active"; }

    //ID GETTER/SETTER
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    //SESSION'S STATUS GETTER/SETTER
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    //SESSION'S CREATION TIME GETTER/SETTER
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    //SESSION'S PLAYERS COUNT GETTER/SETTER
    public int getPlayersCount() { return playersCount; }
    public void setPlayersCount(int players_count) { this.playersCount = players_count; }

    //SESSION'S MAX PLAYER VALUE GETTER/SETTER
    public int getMaxPlayers() { return maxPlayers; }
    public void setMaxPlayers(int max_players) { this.maxPlayers = max_players; }

    //SESSION'S BELONGED USERS GETTER/SETTER
    public Set<User> getUsers() { return users; }
    public void setUsers(Set<User> users) { this.users = users; }

    //SESSION'S BELONGED ROUNDS GETTER/SETTER
    public Set<Round> getRounds() { return rounds; }
    public void setRounds(Set<Round> rounds) { this.rounds = rounds; }

    //USER ADD/REMOVE
    public void addUser(User user) { this.users.add(user); }
    public void removeUser(User user) { this.users.remove(user); }

    //ROUND ADD/REMOVE
    public void addRound(Round round) { this.rounds.add(round); }
    public void removeRound(Round round) { this.rounds.remove(round); }

    //ADDITIONAL METHODS
    public boolean isFull(){ return playersCount == maxPlayers; }
}
