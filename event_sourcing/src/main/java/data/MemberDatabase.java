package data;

import events.Event;
import javafx.util.Pair;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MemberDatabase {

    private final Map<Integer, Member> members;
    private final Map<Integer, Membership> memberships;
    private final Map<Integer, Date> curVisits;
    private final Map<Integer, Pair<Date, Date>> prevVisits;

    public MemberDatabase() {
        members = new HashMap<>();
        memberships = new HashMap<>();
        curVisits = new HashMap<>();
        prevVisits = new HashMap<>();
    }

    public Member getMember(int memberId) {
        if (!members.containsKey(memberId)) {
            throw new IllegalArgumentException(String.format("The member with id %d does not exists", memberId));
        }
        return members.get(memberId);
    }

    public Membership getMembership(int membershipId) {
        if (!memberships.containsKey(membershipId)) {
            throw new IllegalArgumentException(String.format("The membership with id %d does not exists", membershipId));
        }
        return memberships.get(membershipId);
    }

    public void addMember(Member member) {
        members.put(member.getId(), member);
    }

    public void addMembership(Membership membership) {
        memberships.put(membership.getId(), membership);
    }

    public void renewMembership(int membershipId, Date date) {
        if (!memberships.containsKey(membershipId)) {
            throw new IllegalArgumentException(String.format("The membership with id %d does not exists", membershipId));
        }
        memberships.get(membershipId).renew(date);
    }

    public void enterMember(int memberId, Date date) {
        if (!members.containsKey(memberId)) {
            throw new IllegalArgumentException(String.format("The member with id %d does not exists", memberId));
        }
        curVisits.put(memberId, date);
    }

    public void exitMember(int memberId, Date date) {
        if (!curVisits.containsKey(memberId)) {
            throw new IllegalArgumentException(String.format("The member with id %d does not exists", memberId));
        }
        prevVisits.put(memberId, new Pair<>(curVisits.remove(memberId), date));
    }

    public void processEvents(Collection<Event> eventCollection) {
        for (Event e : eventCollection) {
            e.process(this);
        }
    }
}
