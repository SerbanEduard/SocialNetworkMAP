package DTO;

import java.time.LocalDateTime;

public class FriendDTO {
    private int id;
    private String username;
    private LocalDateTime date;

    public FriendDTO(int id, String username, LocalDateTime date) {
        this.id = id;
        this.username = username;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "FriendDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", date=" + date +
                '}';
    }
}
