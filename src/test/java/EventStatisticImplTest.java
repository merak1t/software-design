import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class EventStatisticImplTest {
    private Timer clock;
    private EventStatistic eventStatistic;

    @Before
    public void setUp() {
        clock = new Timer(Instant.now());
        eventStatistic = new EventStatisticImpl(clock);
    }

    @Test
    public void statisticWithNotExistingNameTest() {
        assertThat(eventStatistic.getEventStatisticByName("Mr. Nobody")).isZero();
    }

    @Test
    public void statisticWithNameTest() {
        eventStatistic.incEvent("First");
        eventStatistic.incEvent("First");
        eventStatistic.incEvent("Second");

        assertThat(eventStatistic.getEventStatisticByName("First"))
                .isEqualTo(1.0 / 30);
    }

    @Test
    public void sleepEventTest() {
        eventStatistic.incEvent("ToSleep");
        clock.move(1, ChronoUnit.HOURS);

        assertThat(eventStatistic.getEventStatisticByName("ToSleep")).isZero();
    }

    @Test
    public void allStatsTest() {
        eventStatistic.incEvent("30");
        clock.move(30, ChronoUnit.MINUTES);

        eventStatistic.incEvent("60");
        eventStatistic.incEvent("60");
        clock.move(30, ChronoUnit.MINUTES);

        eventStatistic.incEvent("New");

        assertThat(eventStatistic.getAllEventStatistic())
                .containsOnlyKeys("60", "New");
        assertThat(eventStatistic.getAllEventStatistic())
                .containsEntry("60", 1.0 / 30);
        assertThat(eventStatistic.getAllEventStatistic())
                .containsEntry("New", 1.0 / 60);
    }

    @Test
    public void printStatisticTest() throws IOException {
        var stats = Mockito.mock(EventStatistic.class);

        eventStatistic.incEvent("Profile");

        clock.move(30, ChronoUnit.MINUTES);

        eventStatistic.incEvent("New");
        eventStatistic.incEvent("New");
        eventStatistic.printStatistic();
        //Redirect System.out to buffer
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bo));
        eventStatistic.printStatistic();
        //stats.printStatistic();
        bo.flush();
        String allWrittenLines = bo.toString();
        System.out.println(allWrittenLines);
        assertTrue(allWrittenLines.contains("\"New\" Stats: 0.033333\n" +
                "\"Profile\" Stats: 0.016667\n"));
    }

}