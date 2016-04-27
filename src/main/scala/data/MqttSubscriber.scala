package data

import java.net.InetSocketAddress
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import net.sigusr.mqtt.api._


class MqttSubscriber(topics: Vector[String]) extends Actor {

  val stopTopic: String = s"localSubscriber/stop"

  context.actorOf(Manager.props(new InetSocketAddress("wangdrew.net", 1883))) ! Connect("localSubscriber")

  println("local subscriber creation")
  def receive: Receive = {
    case Connected ⇒
      println("Successfully connected to localhost:1883")
      sender() ! Subscribe((stopTopic +: topics) zip Vector.fill(topics.length + 1) { AtMostOnce }, 1)
      context become ready(sender())
    case ConnectionFailure(reason) ⇒
      println(s"Connection to localhost:1883 failed [$reason]")
  }

  def ready(mqttManager: ActorRef): Receive = {
    case Subscribed(vQoS, MessageId(1)) ⇒
      println("Successfully subscribed to topics:")
      println(topics.mkString(" ", ",\n ", ""))
    case Message(`stopTopic`, _) ⇒
      mqttManager ! Disconnect
      context become disconnecting
    case Message(topic, payload) ⇒
      val message = new String(payload.to[Array], "UTF-8")
      println(s"[$topic] $message")
  }

  def disconnecting(): Receive = {
    case Disconnected ⇒
      println("Disconnected from localhost:1883")
  }
}