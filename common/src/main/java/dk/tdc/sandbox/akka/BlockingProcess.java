package dk.tdc.sandbox.akka;


import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockingProcess {

    private Logger logger = LoggerFactory.getLogger("dk.tdc.sandbox.akka");
    public String block(final String msg) {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace(); // Whatever
        }
        logger.debug("RETURNING ...");
        return Util.filterOutNumbers(msg);
    }

}
