import aggregator.ResponseAggregator;

public class Main {

    public static void main(String[] args) {
        var aggregator = new ResponseAggregator();
        aggregator.aggregate("test");
    }

}