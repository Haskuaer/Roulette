package ef.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest extends Request {

    private String username;
    private String password;
    private String confirmedPassword;

    @JsonCreator
    public RegisterRequest(
            @JsonProperty("action") String action,
            @JsonProperty("username") String username,
            @JsonProperty("password")String password,
            @JsonProperty("confirmedPassword") String confirmedPassword)
    {
        super(action);
        this.username = username;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmedPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmedPassword = confirmPassword; }
}
