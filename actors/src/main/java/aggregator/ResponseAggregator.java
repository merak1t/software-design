package aggregator;

import actor.MasterActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.List;

public class ResponseAggregator {

    private static List<Response> responseList;

    public ResponseAggregator() {}

    public void aggregate(String query) {
        var system = ActorSystem.create("aggregation");
        var master = system.actorOf(Props.create(MasterActor.class), "master");
        master.tell(query, ActorRef.noSender());
    }

    public static void setResponses(List<Response> responseList) {
        ResponseAggregator.responseList = responseList;
    }

    public static List<Response> getResponses() {
        return responseList;
    }

}