name := "RESOLVEWebIDE-common"

version := "1.0"

scalaVersion := "2.11.7"

scalacOptions ++= Seq(
  "-feature" // Shows warnings in detail in the stdout
)

// Javac compiler warning
javacOptions ++= Seq(
  "-Xlint:unchecked",
  "-Xlint:deprecation",
  "-Xdiags:verbose"
)

libraryDependencies ++= Seq(
  // Play Framework
  javaJdbc,
  javaJpa,
  cache,
  javaWs,

  // Email
  "org.jvnet.mock-javamail" % "mock-javamail" % "1.9" % "test",
  "com.typesafe.play" %% "play-mailer" % "3.0.1",

  // Authentication
  "ws.securesocial" % "securesocial_2.11" % "3.0-M4",
  "be.objectify" %% "deadbolt-java" % "2.4.4",

  // Database
  "org.hibernate" % "hibernate-entitymanager" % "5.0.7.Final",
  "mysql" % "mysql-connector-java" % "5.1.38"
)

routesGenerator := InjectedRoutesGenerator