package ef.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class AddFundsRequest extends AuthRequest{

    private double amount;

    @JsonCreator
    public AddFundsRequest(
            @JsonProperty("action") String action,
            @JsonProperty("userId") UUID userId,
            @JsonProperty("amount") double amount)
    {
        super(action, userId);
        this.amount = amount;
    }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
