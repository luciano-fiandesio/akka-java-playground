package dk.tdc.sandbox.akka

/**
 * Created by IntelliJ IDEA.
 * User: lfi
 * Date: 18-10-2010
 * Time: 16:11:38
 * To change this template use File | Settings | File Templates.
 */

import se.scalablesolutions.akka.servlet.AkkaLoader
import javax.servlet.{ServletContextEvent, ServletContextListener}
import se.scalablesolutions.akka.remote.BootableRemoteActorService
import se.scalablesolutions.akka.actor.BootableActorLoaderService
import se.scalablesolutions.akka.util.{Logging, Bootable}
class MyInitializer extends ServletContextListener {

  lazy val loader = new AkkaLoader

   def contextDestroyed(e: ServletContextEvent): Unit =
     loader.shutdown

   def contextInitialized(e: ServletContextEvent): Unit =
     loader.boot(true, new BootableActorLoaderService with BootableRemoteActorService )
 }