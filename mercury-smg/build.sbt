name := "mercury-smg"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.10.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.parboiled" %% "parboiled-scala" % "1.1.5"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.2.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"
