name := "htwg-scala-checkers"
organization := "RuGo"
version := "0.0.1"
scalaVersion := "2.11.8"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")

resolvers += Resolver.jcenterRepo
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

libraryDependencies ++= {
  val scalaTestV = "3.0.0-M15"
  val scalaMockV = "3.2.2"
  Seq(
    "org.scalatest" %% "scalatest" % scalaTestV % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockV % "test",
    "com.escalatesoft.subcut" %% "subcut" % "2.1"
  )
}
libraryDependencies += "com.typesafe.akka" % "akka-remote_2.11" % "2.4.12"

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.11+"

fork in run := true
