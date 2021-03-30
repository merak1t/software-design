import data.MemberDatabase;
import data.Membership;
import events.Event;
import events.MembershipEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventProcessor {

    private final List<Event> eventLog;

    EventProcessor() {
        eventLog = new ArrayList<>();
    }

    public void processEvent(Event e, MemberDatabase database) {
        e.process(database);
        eventLog.add(e);
    }

    public List<Event> getEventLog() {
        return eventLog;
    }

    public Membership getLatestMembershipInfo(int membershipId) {
        List<Event> events = eventLog.stream()
                .filter(e -> e instanceof MembershipEvent && ((MembershipEvent) e).getMembershipId() == membershipId)
                .collect(Collectors.toList());
        MemberDatabase db = new MemberDatabase();
        db.processEvents(events);
        return db.getMembership(membershipId);
    }

}
