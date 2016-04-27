import http.HttpServer
import data.MqttSubscriber

import java.net.InetSocketAddress
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import net.sigusr.mqtt.api._

object Main extends App{
  val config =
    """akka {
         loglevel = INFO
         actor {
            debug {
              receive = off
              autoreceive = off
              lifecycle = off
            }
         }
       }
    """

  val system = ActorSystem("mqttTest", ConfigFactory.parseString(config))
  val powerTopic = Vector("power")
  val powerSubscriber = system.actorOf(Props(classOf[MqttSubscriber], powerTopic))

}