package Domain;

import Utils.Status;

import java.time.LocalDateTime;

public class Friend extends Entity<Tuple<Integer, Integer>>{
    private LocalDateTime dateaAdded;
    private Status status;

    public Friend(LocalDateTime dateaAdded, Status status){
        this.dateaAdded = dateaAdded;
        this.status = status;
    }

    public LocalDateTime getDateaAdded() {
        return dateaAdded;
    }

    public void setDateaAdded(LocalDateTime dateaAdded) {
        this.dateaAdded = dateaAdded;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Friend[id=%s | dateaAdded=%s | status=%s]", getId().toString(), dateaAdded, status);
    }
}
