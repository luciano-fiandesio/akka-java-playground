package dk.tdc.sandbox.akka.typed;

import akka.actor.TypedActor;
import dk.tdc.sandbox.akka.BlockingProcess;
import akka.dispatch.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerServiceImpl extends TypedActor implements CustomerService   {
    private Logger logger = LoggerFactory.getLogger("dk.tdc.sandbox.akka");
    private BlockingProcess blockingActor = new BlockingProcess();

    @Override
    public Future<String> getCustomer(String id) {
        logger.warn("\n*************\nMessage received\n*************");
        return  future (blockingActor.block(id))  ;
    }
}
