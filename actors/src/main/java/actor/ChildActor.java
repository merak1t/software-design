package actor;

import akka.actor.UntypedActor;
import aggregator.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChildActor extends UntypedActor {

    private final SearchEngines engine;

    ChildActor(SearchEngines engine) {
        this.engine = engine;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            var query = (String) message;
            int SEARCH_RESULTS = 5;
            var url = new URL("https://" + engine.toString() + "/search?number=" + SEARCH_RESULTS + "&query=" + query);
            var jsonResponses = Server.process(url);
            List<Response> responses = new ArrayList<>();
            for (var site : jsonResponses.keySet()) {
                var newResp = new Response(engine, new URL(site), jsonResponses.getString(site));
                System.out.println("         " + newResp.toString());
                responses.add(newResp);
            }
            sender().tell(responses, self());
            getContext().stop(self());
        } else {
            unhandled(message);
        }
    }
}