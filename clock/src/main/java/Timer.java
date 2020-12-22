import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;

public class Timer extends Clock {
    private Instant now;

    public Timer(Instant now) {
        this.now = now;
    }

    @Override
    public ZoneId getZone() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Clock withZone(ZoneId zoneId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Instant instant() {
        return now;
    }

    public void set(Instant now) {
        this.now = now;
    }

    public void move(long amountToAdd, TemporalUnit unit) {
        set(now.plus(amountToAdd, unit));
    }

}