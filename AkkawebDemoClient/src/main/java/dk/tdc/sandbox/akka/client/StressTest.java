package dk.tdc.sandbox.akka.client;


public class StressTest {

    public static void main(String[] args) {

        StressTestFireForget fireForget = new StressTestFireForget();
        //fireForget.start(10);

        StressTestTypedActors typedStress = new StressTestTypedActors();
        typedStress.start(10);

    }
}
