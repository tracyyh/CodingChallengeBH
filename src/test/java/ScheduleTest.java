import codingChallenge.Dealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * tests the class Schedule
 */
public class ScheduleTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private String json;
    private Dealer dealer;

    /**
     * sets up the conditions for testing
     */
    @BeforeEach
    public void setup() {
        json = "{ \"A\": {\"start\": true, \"edges\": {\"B\": 5, \"C\": 7}}, \"B\": {\"edges\": {}}, \"C\": {\"edges\": {}} }";
        System.setOut(new PrintStream(outContent));
    }

    /**
     * tests the method run in the Print class
     * @throws InterruptedException
     */
    @Test
    public void testOutput() throws InterruptedException {
        dealer = new Dealer(json);
        dealer.start();
        assertTrue(outContent.toString().contains("A"));
        Thread.sleep(5*1000);
        assertTrue(outContent.toString().contains("B"));
        Thread.sleep(2*1000);
        assertTrue(outContent.toString().contains("C"));
    }
}
