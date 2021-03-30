import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventSourcingTest {

    private FitnessCentreSystem system;

    @BeforeEach
    void init() {
        system = new FitnessCentreSystem();
    }

    @Test
    public void testEntryUsers() {
        system.managerService.newMember("First");
        system.managerService.newMember("Second");
        system.managerService.newMembership(1, new Date(), new Date(new Date().getTime() + 1000 * 2));
        system.entryService.enterMember(1, 1);
        system.entryService.exitMember(1);
    }

    @Test
    public void testInvalidEntries() throws InterruptedException {
        system.managerService.newMember("First");
        system.managerService.newMember("Second");
        system.managerService.newMembership(1, new Date(), new Date(new Date().getTime() + 1000 * 2));
        Thread.sleep(2000);
        try {
            system.entryService.enterMember(1, 1);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("has expired"));
        }
        try {
            system.entryService.enterMember(2, 1);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("can't enter with not owned membership"));
        }
        try {
            system.entryService.enterMember(2, 2);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("The membership with id 2 does not exists"));
        }
    }

    @Test
    public void testMembershipRenew() {
        system.managerService.newMember("First");
        Date date = new Date();
        Date endDate = new Date(date.getTime() + 1000 * 2);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        system.managerService.newMembership(1, date, endDate);
        assertTrue(system.managerService.getMembershipInfo(1)
                .contains(String.format("Membership ID: 1, owner ID: 1, valid from %s to %s",
                        dateFormat.format(date),
                        dateFormat.format(endDate))));
        endDate = new Date(date.getTime() + 1000 * 100);
        system.managerService.renewMembership(1, endDate);
        assertTrue(system.managerService.getMembershipInfo(1)
                .contains(String.format("Membership ID: 1, owner ID: 1, valid from %s to %s",
                        dateFormat.format(date),
                        dateFormat.format(endDate))));
    }

    @Test
    public void testStatistics() {
        system.managerService.newMember("First");
        system.managerService.newMember("Second");
        system.managerService.newMembership(1, new Date(), new Date(new Date().getTime() + 1000 * 1000000));
        system.managerService.newMembership(2, new Date(), new Date(new Date().getTime() + 1000 * 1000000));
        Date date = new Date();
        system.entryService.enterMemberAt(1, 1, date);
        date = new Date(date.getTime() + 1000 * 60 * 6);
        system.entryService.exitMemberAt(1, date);  // 6 minutes
        system.entryService.enterMemberAt(2, 2, date);
        system.entryService.exitMemberAt(2, new Date(date.getTime() + 1000 * 60 * 8));  // 8 minutes
        system.entryService.enterMemberAt(1, 1, new Date(date.getTime() + 1000 * 60 * 3));
        assertTrue(system.statsService.getAverageTime().contains("2 visits: 7.000000 minutes"));
        system.entryService.exitMemberAt(1, new Date(date.getTime() + 1000 * 60 * 13));  // 13 minutes
        system.entryService.enterMemberAt(1, 1, new Date(date.getTime() + 1000 * 60 * 1227)); // next day
        system.entryService.exitMemberAt(1, new Date(date.getTime() + 1000 * 60 * 1228));  // 1 minute
        assertTrue(system.statsService.getStatsForEachDay().contains("3 visitors"));
        assertTrue(system.statsService.getStatsForEachDay().contains("1 visitors"));
    }

}
