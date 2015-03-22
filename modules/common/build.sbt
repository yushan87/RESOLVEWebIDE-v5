name := "RESOLVEWebIDE-common"

version := "1.0"

scalaVersion := "2.11.1"

scalacOptions ++= Seq(
  "-feature" // Shows warnings in detail in the stdout
)

libraryDependencies ++= Seq(
  "org.hibernate" % "hibernate-entitymanager" % "4.3.8.Final",
  "mysql" % "mysql-connector-java" % "5.1.34",
  "com.feth" %% "play-authenticate" % "0.6.8"
)