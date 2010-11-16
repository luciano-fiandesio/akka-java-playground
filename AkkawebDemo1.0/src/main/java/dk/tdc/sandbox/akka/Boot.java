package dk.tdc.sandbox.akka;

import akka.actor.ActorRef;
import akka.actor.SupervisorFactory;
import akka.actor.UntypedActor;
import akka.config.RemoteAddress;
import akka.config.Supervision;
import akka.config.Supervision.*;
import java.util.ArrayList;
import java.util.List;


public class Boot {

    static {
        List<ActorRef> l = new ArrayList<ActorRef>();
        //l.add(UntypedActor.actorOf(FireForgetActor.class));
        for (int i = 0;i<10000;i++) {
           // l.add(UntypedActor.actorOf(FireForgetWithRefActor.class));
        }

        SupervisorFactory sf = new SupervisorFactory(createSupervisorConfig(l));
        //sf.newInstance().start();
    }

    public static SupervisorConfig createSupervisorConfig(List<ActorRef> toSupervise) {
            ArrayList<Server> targets = new ArrayList<Server>(toSupervise.size());
            for (ActorRef ref : toSupervise) {
                targets.add(new Supervise(ref, Supervision.permanent(), new
                        RemoteAddress("localhost", 9919)));

                ref.start();
            }

            return new SupervisorConfig(new AllForOneStrategy(new Class[]{
                    Exception.class}, 50, 1000), targets.toArray(new Server[0]));
        }
}
