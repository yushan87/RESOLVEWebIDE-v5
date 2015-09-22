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
  "org.hibernate" % "hibernate-entitymanager" % "5.0.1.Final",
  "mysql" % "mysql-connector-java" % "5.1.36",
  "com.feth" %% "play-authenticate" % "0.7.0-SNAPSHOT"
)

resolvers ++= Seq(
  "play-authenticate (snapshot)" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

routesGenerator := InjectedRoutesGenerator