name := "AkkaHttpJwtAuthDirective"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(

  "com.typesafe.akka" % "akka-http_2.11" % "10.0.5",

  "com.typesafe.akka" % "akka-http-experimental_2.11" % "2.4.11",

  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.5",

  "com.typesafe.akka" % "akka-http-testkit_2.11" % "10.0.5",

  "com.pauldijou" %% "jwt-core" % "0.12.1",

  "org.json4s" % "json4s-jackson_2.11" % "3.5.1")



