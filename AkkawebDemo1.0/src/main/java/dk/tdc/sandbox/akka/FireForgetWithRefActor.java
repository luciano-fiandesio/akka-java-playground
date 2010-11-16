package dk.tdc.sandbox.akka;



import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FireForgetWithRefActor extends UntypedActor {
    //private ThreadBasedDispatcher d = Dispatchers.newThreadBasedDispatcher(getContext());
    private BlockingProcess a = new BlockingProcess();
    Logger logger = LoggerFactory.getLogger("dk.tdc.sandbox.akka");

    {

        //getContext().setDispatcher(Dispatchers.newExecutorBasedEventDrivenWorkStealingDispatcher("hello-disp").build());
       // getContext().setDispatcher(Dispatchers.newThreadBasedDispatcher(getContext()));
    }

    public void onReceive(Object message) {
        logger.debug("Message received!, now reply with REF");
        System.out.println(getContext().getDispatcher().getClass().getName());
        if (message instanceof String) {
            ActorRef sender = (ActorRef) getContext().getSender().get();
            String msg = (String) message;
            sender.sendOneWay("RESPONSE: " +a.block(msg));
        }
    }
}
