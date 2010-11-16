package dk.tdc.sandbox.akka
import se.scalablesolutions.akka.actor.{Transactor, SupervisorFactory, Actor}
import se.scalablesolutions.akka.actor.Actor._
import se.scalablesolutions.akka.config.ScalaConfig._

class ScalaBoot {

  val factory = SupervisorFactory(
    SupervisorConfig(
      RestartStrategy(OneForOne, 3, 100,List(classOf[Exception])),
      Supervise(
        actorOf[MyActor3],
         LifeCycle(Permanent),RemoteAddress("localhost", 9999)) :: Nil))

  factory.newInstance.start

}