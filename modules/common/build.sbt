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
  "com.typesafe.play" %% "play-mailer" % "5.0.0",

  // Database
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  "mysql" % "mysql-connector-java" % "5.1.39",

  // Deadbolt 2 (Authenticator)
  "be.objectify" % "deadbolt-java_2.11" % "2.5.3"
)