name := "RESOLVEWebIDE-admin"

version := "1.0"

scalaVersion := "2.11.7"

scalacOptions ++= Seq(
  "-feature", // Shows warnings in detail in the stdout
  "-language:reflectiveCalls" 
)

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq()