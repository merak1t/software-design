package events;

import data.MemberDatabase;

import java.util.Date;

public class LeaveEvent extends ModifyEvent {

    public LeaveEvent(int memberId) {
        this.memberId = memberId;
    }

    public LeaveEvent(int memberId, Date at) {
        date = at;
        this.memberId = memberId;
    }

    public void process(MemberDatabase database) {
        if (database != null) {
            database.exitMember(memberId, date);
        }
    }

}
