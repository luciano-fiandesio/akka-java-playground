package dk.tdc.sandbox.akka.client;

import akka.actor.UntypedActor;
import akka.actor.ActorRef;


import akka.remote.RemoteClient;


public class MyClientActor extends UntypedActor {

    public void onReceive(Object message) {

        //System.out.println("message received --> " + message);
        String msg = (String) message;
        long start = System.currentTimeMillis();
        ActorRef actor = RemoteClient.actorFor("dk.tdc.sandbox.akka.MyActor1", "localhost", 9999);
        // Sending via request reply, this actor will be blocked waiting...
        Object o = actor.sendRequestReply(msg);
        long over = System.currentTimeMillis();
        System.out.println("RESPONSE : " + o + " " + (over -start));



        //getContext().replyUnsafe(o);
        // send one way (non blocking) TODO
        //actor.sendOneWay(msg);

    }

}
