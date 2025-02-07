package ef.models;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "users")
public class User {

    //USER ID
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")  // Przechowywanie UUID jako BINARY(16)
    private UUID id;

    //USERNAME
    @Column(name = "username")
    private String username;

    //PASSWORD
    @Column(name = "password")
    private String password;

    //BALANCE
    @Column(name = "balance")
    private double balance;

    //SESSION
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    private GameSession session;

    //ROUND
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id", referencedColumnName = "id")
    private Round round;

    //USER'S BETS
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoundBet> roundBets = new HashSet<>();

    //CONSTRUCTORS
    public User() {}
    public User(String username, String password)
    {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
    }

    //ID GETTER/SETTER
    public UUID getId(){ return id; }
    public void setId(UUID id){ this.id = id; }

    //USERNAME GETTER/SETTER
    public String getUsername(){ return username; }
    public void setUsername(String username){ this.username = username; }

    //PASSWORD GETTER/SETTER
    public String getPassword(){ return password; }
    public void setPassword(String password){ this.password = password; }

    //BALANCE GETTER/SETTER
    public double getBalance(){ return balance; }
    public void setBalance(double balance){ this.balance = balance; }

    //BELONGED SESSION GETTER/SETTER
    public GameSession getSession(){ return session; }
    public void setSession(GameSession session){ this.session = session; }

    //BELONGED ROUND GETTER/SETTER
    public Round getRound(){ return round; }
    public void setRound(Round round){ this.round = round; }

    //USER'S BETS GETTER/SETTER
    public Set<RoundBet> getRoundBets(){ return roundBets; }
    public void setRoundBets(){ this.roundBets = new HashSet<>(); }

    //USER'S BET GETTER/SETTER
    public void addBet(RoundBet roundBet){ this.roundBets.add(roundBet); }
    public void removeBet(RoundBet roundBet){ this.roundBets.remove(roundBet); }
}
