name := "RESOLVEWebIDE-common"

version := "1.0"

scalaVersion := "2.11.1"

scalacOptions ++= Seq(
  "-feature" // Shows warnings in detail in the stdout
)

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final",
  "mysql" % "mysql-connector-java" % "5.1.34"
)