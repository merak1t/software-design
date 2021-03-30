package events;

import data.MemberDatabase;

import java.util.Date;

public class EntryEvent extends ModifyEvent {

    private final int membershipId;

    public EntryEvent(int memberId, int membershipId) {
        this.memberId = memberId;
        this.membershipId = membershipId;
    }

    public EntryEvent(int memberId, int membershipId, Date date) {
        this.date = date;
        this.memberId = memberId;
        this.membershipId = membershipId;
    }

    public void process(MemberDatabase database) {
        if (database != null) {
            database.enterMember(memberId, date);
        }
    }

    public int getMembershipId() {
        return membershipId;
    }

}
