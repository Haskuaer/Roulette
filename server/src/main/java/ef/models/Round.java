package ef.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "rounds")
public class Round {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")  // Przechowywanie UUID jako BINARY(16)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false, referencedColumnName = "id")
    private GameSession session;

    @ManyToMany
    @JoinTable(
            name = "rounds_users",
            joinColumns =  @JoinColumn(name = "round_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @Column(name = "status")
    private String status;

    @Column(name = "createAt")
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoundBet> bets = new HashSet<>();

    public Round() { this.id = UUID.randomUUID(); }
    public Round(GameSession session)
    {
        this.id = UUID.randomUUID();
        this.session = session;
        this.status = "waiting";
        this.createAt = LocalDateTime.now();
        this.bets = new HashSet<>();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Set<User> getUsers() { return users; }
    public void addUser(User user) { users.add(user); }
    public void removeUser(User user) { users.remove(user); }

    public GameSession getSession() { return session; }
    public void setSession(GameSession session) { this.session = session; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreateAt() { return createAt; }
    public void setCreateAt(LocalDateTime createAt) { this.createAt = createAt; }

    public Set<RoundBet> getBets() { return bets; }
    public void addBet(RoundBet bet) { bets.add(bet); bet.setRound(this); }
    public void removeBet(RoundBet bet) { bets.remove(bet); }
}
