package data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Membership {

    private final int ownerId;
    private final int id;
    private final Date from;
    private Date to;

    public Membership(int ownerId, int id, Date from, Date to) {
        this.ownerId = ownerId;
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getId() {
        return id;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public void renew(Date to) {
        this.to = to;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return String.format("Membership ID: %d, owner ID: %d, valid from %s to %s",
                id, ownerId, dateFormat.format(from), dateFormat.format(to));
    }
}
