package DTO;

import java.time.LocalDateTime;

public class FriendDTO {
    private String username;
    private LocalDateTime date;

    public FriendDTO(String username, LocalDateTime date) {
        this.username = username;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
