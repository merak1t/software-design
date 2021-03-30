import data.Membership;
import events.EntryEvent;
import events.LeaveEvent;

import java.util.Date;

public class EntryService {

    private final EventProcessor processor;

    EntryService(EventProcessor processor) {
        this.processor = processor;
    }


    private void tryEnter(int memberId, int membershipId) {
        Membership membership = processor.getLatestMembershipInfo(membershipId);
        if (membership.getOwnerId() != memberId) {
            throw new IllegalArgumentException(String.format("Member with id %d can't enter with not owned membership with id %d", memberId, membershipId));
        }
        if (membership.getTo().compareTo(new Date()) < 0) {
            throw new IllegalArgumentException(String.format("The membership with id %d has expired", membershipId));
        }
    }

    public void enterMember(int memberId, int membershipId) {
        tryEnter(memberId, membershipId);
        processor.processEvent(new EntryEvent(memberId, membershipId), null);
    }

    public void enterMemberAt(int memberId, int membershipId, Date at) {
        tryEnter(memberId, membershipId);
        processor.processEvent(new EntryEvent(memberId, membershipId, at), null);
    }

    public void exitMember(int memberId) {
        processor.processEvent(new LeaveEvent(memberId), null);
    }

    public void exitMemberAt(int memberId, Date at) {
        processor.processEvent(new LeaveEvent(memberId, at), null);
    }

}
