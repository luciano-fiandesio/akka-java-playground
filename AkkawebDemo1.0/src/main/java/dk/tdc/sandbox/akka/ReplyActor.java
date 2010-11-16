package dk.tdc.sandbox.akka;

import akka.actor.UntypedActor;


public class ReplyActor extends UntypedActor {

    public void onReceive(Object message) {

        if (message instanceof String) {
            String msg = (String) message;
            getContext().replyUnsafe(Util.filterOutNumbers(msg));
        }  else {
             getContext().replyUnsafe("001 - invalid message");
        }

        String msg = (String) message;
        //String body = msg.bodyAs(String.class);
        System.out.println(String.format("received %s", msg));

        if (msg.startsWith("reply")) {
            getContext().replyUnsafe(msg + " from " + getContext().getUuid());
        } else {
           getContext().replyUnsafe("invalid ["+msg+"]"); 
        }
    }


}
