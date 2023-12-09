import codingChallenge.Dealer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * tests the Dealer class
 */
public class DealerTest {
    private ByteArrayOutputStream testLog;
    private String formattedGraph;
    private String graph;
    private JsonNode graphNode;

    /**
     * setups the conditions for testing
     */
    @BeforeEach
    public void setup() {
        this.testLog = new ByteArrayOutputStream(2048);
        graph = "{ \"A\": {\"start\": true, \"edges\": {\"B\": 5, \"C\": 7}}, \"B\": {\"edges\": {}}, \"C\": {\"edges\": {}} }";
        formattedGraph = "{\n"
                + "\t\"A\": {\"start\": true, \"edges\": {\"B\": 5, \"C\": 7}},\n"
                + "\t\"B\": {\"edges\": {}},\n"
                + "\t\"C\": {\"edges\": {}}\n"
                + "}";
        assertEquals("", logToString());
    }

    /**
     * Converts the ByteArrayOutputStream log to a string in UTF_8 format
     *
     * @return String representing the current log buffer
     */
    private String logToString() {
        return testLog.toString(StandardCharsets.UTF_8);
    }

    /**
     * tests the method Start given a JSON Object
     */
    @Test
    public void testStartString() {
        Dealer dealer = new Dealer(graph);
        ObjectMapper mapper = new ObjectMapper();
        try {
            graphNode = mapper.readValue(graph, JsonNode.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dealer.start();
        assertEquals(dealer.getGraph().toString(), graphNode.toString());
    }

    /**
     * tests the method Start given a file name
     */
    @Test
    public void testStartFile() {
        String filePath = "/Users/trcxi/CodingChallengeBH/graph.json";
        Dealer dealer = new Dealer(filePath);
        ObjectMapper mapper = new ObjectMapper();
        try {
            graphNode = mapper.readValue(new File(filePath), JsonNode.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dealer.start();

        assertEquals(dealer.getGraph().toString(), graphNode.toString());
    }

    /**
     * tests the method Start given a non JSON object
     */
    @Test
    public void testExceptionJson() {
        String path = "randomPath-name";
        Assertions.assertThrows(RuntimeException.class, () -> {
            Dealer d = new Dealer(path);
            d.start();
        });
    }

    /**
     * tests the method Start given a non file name
     */
    @Test
    public void testExceptionFile() {
        String path = "/randomPath-name";
        Assertions.assertThrows(RuntimeException.class, () -> {
            Dealer d = new Dealer(path);
            d.start();
        });
    }
}
