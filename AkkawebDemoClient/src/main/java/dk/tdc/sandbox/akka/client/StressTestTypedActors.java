package dk.tdc.sandbox.akka.client;


import akka.actor.TypedActorFactory;
import akka.actor.TypedActor;
import akka.dispatch.Dispatchers;
import dk.tdc.sandbox.akka.typed.CustomerService;
import dk.tdc.sandbox.akka.typed.CustomerServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import akka.dispatch.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StressTestTypedActors {
    private static Logger logger = LoggerFactory.getLogger("dk.tdc.sandbox.akka.client");
    public void start(int actors) {

        List<Future<String>> futures = new ArrayList<Future<String>>();

        logger.warn("....Start typed actor stress test");
        CustomerService[] services = startRing(actors);
        logger.warn("Created {} remote actors.", services.length);
        long initiateMethodCall = System.currentTimeMillis();

        for (CustomerService ref : services) {
            long beforeM = System.currentTimeMillis();
            futures.add(ref.getCustomer(UUID.randomUUID().toString()));

            //System.out.println(future.result().get());
            long afterM = System.currentTimeMillis();
            logger.warn("milliseconds to call a typed actor: {}" , (afterM - beforeM));
        }

        for (Future<String> f : futures) {
            if (f.isCompleted()) {
                logger.warn("Completed");
                logger.warn("RESPONSE; + " + f.result().get());
            } else {
                logger.warn("Incomplete");
                f.awaitBlocking();  // .await() seems buggy
                //f.await();
            }
        }
        long terminateMethodCall = System.currentTimeMillis();
        logger.warn("\n==========================\n" + getElapsedTimeHoursMinutesSecondsString(terminateMethodCall - initiateMethodCall) + "\n==========================\n");
    }

    private static CustomerService[] startRing(int n) {

        logger.warn("Spawning actors...");
        long startConstructing = System.currentTimeMillis();
        CustomerService[] nodes = new CustomerService[n];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = (CustomerService) TypedActor.newRemoteInstance(CustomerService.class, CustomerServiceImpl.class, 7000, "localhost", 9919);
            nodes[i].getCustomer(UUID.randomUUID().toString());
        }

        long endConstructing = System.currentTimeMillis();

        logger.warn("constructing :" + (endConstructing - startConstructing));

        return nodes;
    }

    public String getElapsedTimeHoursMinutesSecondsString(long millis) {
        long elapsedTime = millis;
        String format = String.format("%%0%dd", 2);
        elapsedTime = elapsedTime / 1000;
        String seconds = String.format(format, elapsedTime % 60);
        String minutes = String.format(format, (elapsedTime % 3600) / 60);
        String hours = String.format(format, elapsedTime / 3600);
        String time = hours + ":" + minutes + ":" + seconds;
        return time;
    }

}
