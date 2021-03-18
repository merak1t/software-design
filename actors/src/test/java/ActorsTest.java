
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import aggregator.ResponseAggregator;
import aggregator.Server;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActorsTest {

    private Server server;

    @BeforeEach
    void init() {
        server = new Server();
        ResponseAggregator.setResponses(null);
    }

    @Test
    public void noTimeout() throws InterruptedException {
        ResponseAggregator aggregator = new ResponseAggregator();
        aggregator.aggregate("test");
        Thread.sleep(3000);
        assertEquals(15, ResponseAggregator.getResponses().size());
    }

    @Test
    public void bigTimeout() throws InterruptedException {
        ResponseAggregator aggregator = new ResponseAggregator();
        server.setTimeout(600);
        aggregator.aggregate("test");
        Thread.sleep(3000);
        assertEquals(0, ResponseAggregator.getResponses().size());
    }

    @Test
    public void bigTimeoutFast() throws InterruptedException {
        ResponseAggregator aggregator = new ResponseAggregator();
        server.setTimeout(600);
        server.setSpeed(true);
        aggregator.aggregate("test");
        Thread.sleep(3000);
        assertEquals(5, ResponseAggregator.getResponses().size());
    }

}