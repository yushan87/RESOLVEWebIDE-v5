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

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq(
  "org.hibernate" % "hibernate-entitymanager" % "5.0.1.Final",
  "mysql" % "mysql-connector-java" % "5.1.36"
 // "com.feth" %% "play-authenticate_2.11" % "0.6.8"
)