package ef.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "round_bets")
public class RoundBet
{
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id", nullable = false)
    private Round round;

    @Column(name = "bet_type", nullable = false)
    private String betType;

    @Column(name = "bet_amount", nullable = false)
    private double betAmount;

    public RoundBet() { this.id = UUID.randomUUID(); }

    public RoundBet(String betType, double betAmount)
    {
        this.id = UUID.randomUUID();
        this.betType = betType;
        this.betAmount = betAmount;
    }

    public Round getRound() { return round; }
    public void setRound(Round round) { this.round = round; }

    public String getBetType() { return betType; }
    public void setBetType(String betType) { this.betType = betType; }

    public double getBetAmount() { return betAmount; }
    public void setBetAmount(double betAmount) { this.betAmount = betAmount; }
}
