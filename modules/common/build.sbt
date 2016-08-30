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
  javaJdbc,
  javaJpa,
  cache,
  javaWs,
  "com.typesafe.play" %% "play-mailer" % "5.0.0",
  "org.hibernate" % "hibernate-core" % "5.2.2.Final",
  "mysql" % "mysql-connector-java" % "5.1.39"
)

routesGenerator := InjectedRoutesGenerator