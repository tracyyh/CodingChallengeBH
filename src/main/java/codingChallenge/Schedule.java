package codingChallenge;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * creates a schedule to print nodes
 */
class Schedule {
    private JsonNode graph;
    private Timer timer;

    /**
     * constructor
     *
     * @param timer a timer for a specific schedule
     * @param graph the graph
     */
    public Schedule(Timer timer, JsonNode graph) {
        this.timer = timer;
        this.graph = graph;
    }

    /**
     * prints the node
     */
    public class Print extends TimerTask {
        private String thisNode;

        /**
         * constructor
         *
         * @param thisNode the node to be printed
         */
        public Print(String thisNode) {
            this.thisNode = thisNode;
        }

        /**
         * prints the node
         */
        @Override
        public void run() {
            System.out.println(thisNode);
        }
    }

    /**
     * starts printing the adjacent nodes
     *
     * @param contents the adjacent nodes
     */
    public void startPrint(JsonNode contents) {
        JsonNode edges = contents.get("edges");
        Iterator<String> connectedNode = edges.fieldNames();
        while (connectedNode.hasNext()) {
            String thisNode = connectedNode.next();
            JsonNode timeJson = edges.get(thisNode);
            int time = timeJson.asInt();
            JsonNode next = graph.get(thisNode);
            this.timer.schedule(new Print(thisNode), time * 1000);
            startPrint(next);
        }
    }
}


