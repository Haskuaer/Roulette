package ef.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "rounds")
public class Round {

    //ROUND ID
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    //BELONGED SESSION
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false, referencedColumnName = "id")
    private GameSession session;

    //USERS
    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> users = new HashSet<>();

    //BETS
    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoundBet> bets = new HashSet<>();

    //ROUND STATUS
    @Column(name = "status")
    private String status;

    //ROUND CREATION TIME
    @Column(name = "createAt")
    private LocalDateTime createAt;

    //CONSTRUCTORS
    public Round() {}
    public Round(GameSession session)
    {
        this.id = UUID.randomUUID();
        this.session = session;
        this.status = "waiting";
        this.createAt = LocalDateTime.now();
        this.bets = new HashSet<>();
    }

    //ID GETTER/SETTER
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    //CONNECTED USERS GETTER/SETTER
    public Set<User> getUsers() { return users; }
    public void setUsers(Set<User> users) { this.users = users; }

    //BELONGED SESSION GETTER/SETTER
    public GameSession getSession() { return session; }
    public void setSession(GameSession session) { this.session = session; }

    //ROUND STATUS GETTER/SETTER
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    //ROUND CREATION TIME GETTER/SETTER
    public LocalDateTime getCreateAt() { return createAt; }
    public void setCreateAt(LocalDateTime createAt) { this.createAt = createAt; }

    //ROUND'S BETS GETTER/SETTER
    public Set<RoundBet> getBets() { return bets; }
    public void setBets(Set<RoundBet> bets) { this.bets = bets; }

    //USER ADD/REMOVE
    public void addUser(User user) { users.add(user); }
    public void removeUser(User user) { users.remove(user); }

    //BET ADD/REMOVE
    public void addBet(RoundBet bet) { bets.add(bet); bet.setRound(this); }
    public void removeBet(RoundBet bet) { bets.remove(bet); }
}
