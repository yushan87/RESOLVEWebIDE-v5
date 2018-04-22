name := "RESOLVEWebIDE-common"

version := "1.0"

// Scala compiler options
scalacOptions ++= Seq(
  "-feature", // Shows warnings in detail in the stdout
  "-language:reflectiveCalls" 
)

// Javac compiler options
javacOptions ++= Seq(
  "-Xlint:unchecked",
  "-Xlint:deprecation",
  "-Xdiags:verbose"
)

// Managed Dependencies
libraryDependencies ++= Seq(
  // Play Framework
  javaJdbc,
  javaJpa,
  cache,
  filters,
  javaWs,
  "com.typesafe.play" %% "play-mailer" % "6.0.1",

  // Database
  "org.hibernate" % "hibernate-core" % "5.2.16.Final",
  "mysql" % "mysql-connector-java" % "8.0.11",

  // Deadbolt 2 (Authenticator)
  "be.objectify" % "deadbolt-java_2.12" % "2.6.4"
)