package dk.tdc.sandbox.akka

import se.scalablesolutions.akka.actor.Actor

class MyActor4 extends Actor {

  def receive = {
    case "test" => log.info("received test")
    case _ => log.info("received unknown message")
  }
}