package codingChallenge;

/**
 * main driver of the program
 */
public class Main {

    /**
     * entry point
     *
     * @param args an array of the command line arguments
     */
    public static void main(String[] args) {
        Dealer dealer = new Dealer(args[0]);
        dealer.start();
    }
}