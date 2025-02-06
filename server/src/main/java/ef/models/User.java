package ef.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")  // Definicja generatora UUID
    @GenericGenerator(name = "uuid2", strategy = "uuid2")  // Generator UUID2
    @Column(name = "id", columnDefinition = "BINARY(16)")  // Przechowywanie UUID jako BINARY(16)
    private UUID id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "balance")
    private double balance;
    @ManyToMany(mappedBy = "users")
    private Set<Round> rounds = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "session_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id")
    )
    private Set<GameSession> sessions = new HashSet<>();

    public User() {}
    public User(String username, String password) { this.username = username; this.password = password; }

    public UUID getId(){ return id; }
    public void setId(UUID id){ this.id = id; }

    public String getUsername(){ return username; }
    public void setUsername(String username){ this.username = username; }

    public String getPassword(){ return password; }
    public void setPassword(String password){ this.password = password; }

    public double getBalance(){ return balance; }
    public void setBalance(double balance){ this.balance = balance; }

    public Set<Round> getRounds(){ return rounds; }
    public void setRounds(Set<Round> rounds){ this.rounds = rounds; }

    public Set<GameSession> getSessions(){ return sessions; }
    public void setSessions(Set<GameSession> sessions){ this.sessions = sessions; }
}
