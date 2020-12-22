import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventStatisticImpl implements EventStatistic {

    private final Clock clock;
    private final Map<String, List<Instant>> events = new HashMap<>();

    public EventStatisticImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void incEvent(String name) {
        if (!events.containsKey(name)) {
            events.put(name, new ArrayList<>());
        }

        events.get(name).add(clock.instant());
    }

    private double getEventStatisticByName(String name, boolean removeOld) {
        if (removeOld) {
            removeSleepEvents();
        }

        if (!events.containsKey(name)) {
            return 0;
        }
        double minutesInHour = 60;
        return events.get(name).size() / minutesInHour;
    }

    @Override
    public double getEventStatisticByName(String name) {
        return getEventStatisticByName(name, true);
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        removeSleepEvents();
        return events.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> getEventStatisticByName(entry.getKey(), false)
                        )
                );
    }

    @Override
    public void printStatistic() {
        var allEventStatistic = getAllEventStatistic();

        for (var name : allEventStatistic.keySet()) {
            System.out.printf("\"%s\" Stats: %f%n", name, allEventStatistic.get(name));
        }
    }

    private void removeSleepEvents() {
        var instantClock = clock.instant().minus(1, ChronoUnit.HOURS);

        for (String name : events.keySet()) {
            var newInstants = events.get(name).stream()
                    .filter(instant -> instant.isAfter(instantClock))
                    .collect(Collectors.toList());

            events.put(name, newInstants);
        }

        events.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }

}