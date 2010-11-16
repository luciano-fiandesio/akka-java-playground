package dk.tdc.sandbox.akka

import se.scalablesolutions.akka.actor.Actor

class MyActor3 extends Actor {

  def receive = {

    // TODO how to return sender: Option[AnyRef]

    case s: String => self.reply("msg from me " + s )
    case _ => log.info("received unknown message " + "hello dead")
  }
}