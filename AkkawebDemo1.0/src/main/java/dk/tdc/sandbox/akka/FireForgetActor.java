package dk.tdc.sandbox.akka;

import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FireForgetActor extends UntypedActor {
    private Logger logger = LoggerFactory.getLogger("dk.tdc.sandbox.akka");
    public void onReceive(Object message) {

        if (message instanceof String) {
            String msg = (String) message;
            logger.debug("Message received!");

        }  else {
             // DO NOTHING
        }


    }

}
