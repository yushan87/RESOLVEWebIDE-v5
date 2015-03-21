name := "RESOLVEWebIDE"

version := "5.0"

scalaVersion := "2.11.1"

scalacOptions ++= Seq(
  "-feature", // Shows warnings in detail in the stdout
  "-language:reflectiveCalls" 
)

libraryDependencies ++= Seq()
  
lazy val common = (project in file("modules/common"))
  .enablePlugins(PlayJava)
  
lazy val admin = (project in file("modules/admin"))
  .enablePlugins(PlayJava)
  .dependsOn(common)

lazy val main = (project in file("."))
  .enablePlugins(PlayJava)
  .dependsOn(common, admin)
  .aggregate(common, admin)