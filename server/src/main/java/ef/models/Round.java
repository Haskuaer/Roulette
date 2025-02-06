package ef.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.UUID;

@Entity
public class Rounds {

    @Id
    @GeneratedValue(generator = "uuid2")  // Definicja generatora UUID
    @GenericGenerator(name = "uuid2", strategy = "uuid2")  // Generator UUID2
    @Column(name = "id", columnDefinition = "BINARY(16)")  // Przechowywanie UUID jako BINARY(16)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false, referencedColumnName = "id")
    private GameSession session;

    @Column(name = "status")
    private String status;
    @Column(name = "bet_amount")
    private double bet_amount;
    @Column(name = "bet_type")
    private String bet_type;
    @Column(name = "bet_value")
    private double bet_value;
    @Column(name = "round_result")
    private String round_result;
    @Column(name = "win_amount")
    private double win_amount;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public GameSession getSession() { return session; }
    public void setSession(GameSession session) { this.session = session; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getBalance() { return bet_amount; }
    public void setBalance(double balance) { this.bet_amount = balance; }

    public String getBetType() { return bet_type; }
    public void setBetType(String betType) { this.bet_type = betType; }

    public double getBetValue() { return bet_value; }
    public void setBetValue(double betValue) { this.bet_value = betValue; }

    public String getRoundResult() { return round_result; }
    public void setRoundResult(String roundResult) { this.round_result = roundResult; }

    public double getWinAmount() { return win_amount; }
    public void setWinAmount(double winAmount) { this.win_amount = winAmount; }
}
