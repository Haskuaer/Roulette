package ef.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class BalanceResponse extends Response {

    private double balance;

    @JsonCreator
    public BalanceResponse(
            @JsonProperty("status") String status,
            @JsonProperty("userId") UUID userId,
            @JsonProperty("balance") double balance )
    {
        super(status, userId);
        this.balance = balance;
    }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
