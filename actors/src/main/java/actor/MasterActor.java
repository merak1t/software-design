package actor;

import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import aggregator.Response;
import aggregator.ResponseAggregator;
import aggregator.SearchEngines;
import scala.concurrent.duration.Deadline;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;

public class MasterActor extends UntypedActor {

    private final List<Response> responses;
    private int received = 0;
    private Deadline deadline;

    MasterActor() {
        this.responses = new ArrayList<>();
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof String) {
            var query = (String) message;
            for (SearchEngines engine : SearchEngines.values()) {
                var child = getContext().actorOf(Props.create(ChildActor.class, engine), engine.toString());
                child.tell(query, self());
            }
            int TIMEOUT_MS = 300;
            deadline = Duration.create(TIMEOUT_MS, "milliseconds").fromNow();
            getContext().setReceiveTimeout(deadline.timeLeft());
        } else if (message instanceof List) {
            var listResp = (List<Response>) message;
            for (var resp : listResp) {
                System.out.println("Received " + resp.toString());
            }
            responses.addAll(listResp);
            received++;
            getContext().setReceiveTimeout(deadline.timeLeft());
        }
        if (message instanceof ReceiveTimeout || received == SearchEngines.values().length) {
            ResponseAggregator.setResponses(responses);
            getContext().system().terminate();
        }
        if (!(message instanceof List) && !(message instanceof ReceiveTimeout)) {
            unhandled(message);
        }
    }
}