package ef.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "round_bets")
public class RoundBet
{
    //ID
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    //BELONGED ROUND
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id", nullable = false)
    private Round round;

    //BET FIELD
    @Column(name = "bet_type", nullable = false)
    private String betType;

    //BET AMOUNT
    @Column(name = "bet_amount", nullable = false)
    private double betAmount;

    //CONSTRUCTORS
    public RoundBet() { this.id = UUID.randomUUID(); }
    public RoundBet(String betType, double betAmount)
    {
        this.id = UUID.randomUUID();
        this.betType = betType;
        this.betAmount = betAmount;
    }

    //ID GETTER/SETTER
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    //ROUND GETTER/SETTER
    public Round getRound() { return round; }
    public void setRound(Round round) { this.round = round; }

    //TYPE GETTER/SETTER
    public String getBetType() { return betType; }
    public void setBetType(String betType) { this.betType = betType; }

    //BET AMOUNT GETTER/SETTER
    public double getBetAmount() { return betAmount; }
    public void setBetAmount(double betAmount) { this.betAmount = betAmount; }
}
