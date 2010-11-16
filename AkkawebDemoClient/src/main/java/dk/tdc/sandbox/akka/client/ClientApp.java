package dk.tdc.sandbox.akka.client;
import akka.remote.RemoteClient;
import akka.remote.RemoteNode;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.remote.RemoteServer;

import java.util.ArrayList;
import java.util.List;

public class ClientApp {

    public static void main(String[] args) {


        
        //ActorRef actor = RemoteClient.actorFor("dk.tdc.sandbox.akka.MyActor3", "localhost", 9999);
        ActorRef actor = UntypedActor.actorOf(MyClientActor.class);
        actor.start();
        System.out.println("--------------->" + actor.sendRequestReply("reply"));

        //Object res = actor.sendRequestReply(new String("hello world"));

    }


}
