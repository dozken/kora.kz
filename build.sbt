name := """kora"""

version := "0.1-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  //javaWs,
  "be.objectify" %% "deadbolt-java" % "2.3.0-RC1",  
  "org.webjars" %% "webjars-play" % "2.3.0",
  "org.webjars" % "bootstrap" % "3.2.0",
  "org.webjars" % "bootstrap-switch" % "3.0.2",
  "org.webjars" % "bootstrap-datepicker" % "1.3.0-3",
  "org.webjars" % "bootstrapvalidator" % "0.5.0",  
  "org.webjars" % "jquery-ui" % "1.11.1",
  "com.typesafe.play.plugins" %% "play-plugins-mailer" % "2.3.0",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
)

resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns)