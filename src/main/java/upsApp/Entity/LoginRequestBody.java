package upsApp.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequestBody {

    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

    // Constructor
    public LoginRequestBody() {
    }

    public LoginRequestBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setter methods
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
