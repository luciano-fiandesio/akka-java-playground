package dk.tdc.sandbox.akka.client;


public class StressTest {

    public static void main(String[] args) {

        if (args.length==1) {
            if (args[0].equals("untyped")) {
                StressTestFireForget fireForget = new StressTestFireForget();
                fireForget.start(10);

            } else if (args[0].equals("typed")) {
                StressTestTypedActors typedStress = new StressTestTypedActors();
                typedStress.start(10);

            }
        } else {
            System.out.println("Don't know which test to run, please pass at least one argument");
        }


    }
}
