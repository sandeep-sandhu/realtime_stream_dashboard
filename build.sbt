name := """Dashboard for Stream Analytics"""

organization := "io.github.sandeep_sandhu"

version := "0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.11"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

libraryDependencies += "redis.clients" % "jedis" % "4.4.3"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "io.github.sandeep_sandhu.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "io.github.sandeep_sandhu.binders._"

