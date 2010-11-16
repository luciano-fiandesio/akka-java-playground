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

public class StressTestTypedActors {

    public void start(int actors) {

        List<Future<String>> futures = new ArrayList<Future<String>>();

        System.out.println("....Start typed actor stress test");
        CustomerService[] services = startRing(actors);
        System.out.println("---------------> " + services.length);
        long initiateMethodCall = System.currentTimeMillis();

        for (CustomerService ref : services) {
            long beforeM = System.currentTimeMillis();
            futures.add(ref.getCustomer(UUID.randomUUID().toString()));

            //System.out.println(future.result().get());
            long afterM = System.currentTimeMillis();
            System.out.println("milliseconds to call a typed actor: " + (afterM - beforeM));
        }

        for (Future<String> f : futures) {
            if (f.isCompleted()) {
                System.out.println("Completed");
                System.out.println("RESPONSE; + " + f.result().get());
            } else {
                System.out.println("Incomplete");
                f.awaitBlocking();  // .await() seems buggy
                //f.await();

                System.out.println("RESPONSE; + " + f.result().get());
            }
        }
        long terminateMethodCall = System.currentTimeMillis();
        System.out.println("\n==========================\n" + getElapsedTimeHoursMinutesSecondsString(terminateMethodCall - initiateMethodCall) + "\n==========================\n");
    }

    private static CustomerService[] startRing(int n) {

        System.out.println("Spawning actors...");
        long startConstructing = System.currentTimeMillis();
        CustomerService[] nodes = new CustomerService[n];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = (CustomerService) TypedActor.newRemoteInstance(CustomerService.class, CustomerServiceImpl.class, 7000, "localhost", 9919);
            nodes[i].getCustomer(UUID.randomUUID().toString());
        }

        long endConstructing = System.currentTimeMillis();

        System.out.println("constructing :" + (endConstructing - startConstructing));

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
