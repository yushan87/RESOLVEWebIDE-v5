name := "RESOLVEWebIDE-common"

version := "1.0"

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
  filters,
  javaWs,
  "com.typesafe.play" %% "play-mailer" % "5.0.0",

  // Database
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  "mysql" % "mysql-connector-java" % "5.1.39",

  // Deadbolt 2 (Authentication/Restriction)
  "be.objectify" %% "deadbolt-java" % "2.5.0"
)

routesGenerator := InjectedRoutesGenerator