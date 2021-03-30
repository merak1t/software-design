package events;

import data.Member;
import data.MemberDatabase;

public class NewMemberEvent extends Event {

    private final String name;
    private final int memberId;

    public NewMemberEvent(String name, int memberId) {
        this.name = name;
        this.memberId = memberId;
    }

    public void process(MemberDatabase database) {
        if (database != null) {
            database.addMember(new Member(name, memberId));
        }
    }

    public String getName() {
        return name;
    }

    public int getMemberId() {
        return memberId;
    }

}
