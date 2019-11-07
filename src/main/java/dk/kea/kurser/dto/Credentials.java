package dk.kea.kurser.dto;

public class Credentials {

    private String email;
    private String secret;

    public Credentials() { }

    public Credentials(String email, String secret) {
        this.email = email;
        this.secret = secret;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
