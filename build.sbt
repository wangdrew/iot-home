
name := "iot-home"

organization := "wangdrew"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-http" % "6.34.0",
  "net.sigusr" %% "scala-mqtt-client" % "0.6.0",
  "com.paulgoldbaum" %% "scala-influxdb-client" % "0.4.5"
)