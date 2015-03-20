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
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "org.hibernate" % "hibernate-entitymanager" % "4.3.8.Final",
  "mysql" % "mysql-connector-java" % "5.1.34"
)