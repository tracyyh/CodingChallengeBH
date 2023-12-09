package codingChallenge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Timer;

/**
 * delegates String input to methods
 */
public class Dealer {

    private String json;
    private Iterator<String> nodes;
    JsonNode graph;

    /**
     * constructor
     * @param json the inputted filename or json object
     */
    public Dealer(String json) {
        this.json = json;
    }

    /**
     * returns the graph
     *
     * @return graph
     */
    public JsonNode getGraph() {
        return graph;
    }

    /**
     * starts the program by reading the String input to create a JsonNode
     */
    public void start() {
        ObjectMapper mapper = new ObjectMapper();
        if (json.substring(0, 1).equals("/")) {
            try {
                graph = mapper.readValue(new File(this.json), JsonNode.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else try {
           graph = mapper.readValue(this.json, JsonNode.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        nodes = graph.fieldNames();
        printEdges();
    }

    /**
     * prints the adjacent edges of a node
     */
    public void printEdges() {
        while (nodes.hasNext()) {
            String first = nodes.next();
            JsonNode node = graph.get(first);
            String key = node.fieldNames().next();
            JsonNode firstKey = node.get(key);
            if (firstKey.toString().equals("true")) {
                System.out.println(first);
                Timer timer = new Timer();
                Schedule sched = new Schedule(timer, graph);
                sched.startPrint(node);
            }
        }
    }
}

