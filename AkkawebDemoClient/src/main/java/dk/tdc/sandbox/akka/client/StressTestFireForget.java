package dk.tdc.sandbox.akka.client;


import akka.actor.UntypedActor;
import akka.actor.ActorRef;
import akka.remote.RemoteClient;

import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;

public class StressTestFireForget {

    public void start(int actors) {
        /*
        ActorRef[] refs = startRing(actors, FireForgetActor.class);
        for (ActorRef ref : refs) {
            long beforeM = System.currentTimeMillis();
            //ref.sendOneWay(Long.toHexString(Double.doubleToLongBits(Math.random())));
            long afterM = System.currentTimeMillis();
        }*/
        System.out.println("....Start fire forget with reply");

        ActorRef[] refs = startRing(actors, FireForgetActorWithRef.class);
        int i = 0;
        for (ActorRef ref : refs) {
            long beforeM = System.currentTimeMillis();
            ref.sendOneWay(i + "|"+Long.toHexString(Double.doubleToLongBits(Math.random())));
            long afterM = System.currentTimeMillis();
            System.out.println(afterM - beforeM);
            i++;

        }

        //System.exit(0);
    }

    private static ActorRef[] startRing(int n, Class actorClazz) {

        System.out.println("Spawing actors...");
        long startConstructing = System.currentTimeMillis();
        ActorRef[] nodes = new ActorRef[n];
        for (int i = 0; i < nodes.length; i++) {

            nodes[i] = UntypedActor.actorOf(actorClazz);
            nodes[i].start();
        }

        long endConstructing = System.currentTimeMillis();

        System.out.println("constructing :" + (endConstructing - startConstructing));

        return nodes;
    }


}


class FireForgetActor extends UntypedActor {


    public void onReceive(Object message) {

        final String msg = (String) message;
        long start = System.currentTimeMillis();
        ActorRef actor = RemoteClient.actorFor("dk.tdc.sandbox.akka.FireForgetActor", "localhost", 9919);
        // Sending via request reply, this actor will be blocked waiting...
        actor.sendOneWay(msg);
        long over = System.currentTimeMillis();
        //System.out.println("RESPONSE : " + o + " " + (over -start));

    }

}

class FireForgetActorWithRef extends UntypedActor {
    private final String actorClazzName = "dk.tdc.sandbox.akka.FireForgetWithRefActor";

    public void onReceive(Object message) {

        if (message instanceof String) {
            final String msg = (String) message;
            long start = System.currentTimeMillis();
            if (msg.startsWith("RESPONSE: ")) {
                System.out.println("GOT RESPONSE!!! " + msg);
            } else {

                ActorRef actor = RemoteClient.actorFor(actorClazzName, "localhost", 9919);
                // Sending via request reply, this actor will be blocked waiting...
                actor.sendOneWay(msg, getContext());
                track.add(new Track(System.currentTimeMillis(), 0, new Long(msg.split("\\|")[0])));
            }
            long over = System.currentTimeMillis();
            //System.out.println("RESPONSE : " + o + " " + (over -start));
        } else {
            // error

        }

    }


    class Track {

        public long fired;
        public long received;
        public long id;
        public Track(long _fired,long _received, long _id) {

            this.fired = _fired;
            this.received = _received;
            this.id = _id;
        }
    }

    private final static List<Track> track = new ArrayList<Track>();

}


