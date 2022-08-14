import java.time.LocalDateTime;

public class Session{
    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    String username;
    int id;
    LocalDateTime expires;
}