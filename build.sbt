name := "RESOLVEWebIDE"

version := "5.0"

def commonSettings = Seq(
  scalaVersion := "2.11.7"
)

scalacOptions ++= Seq(
  "-feature", // Shows warnings in detail in the stdout
  "-language:reflectiveCalls" 
)

// Javac compiler warning
javacOptions ++= Seq(
  "-Xlint:unchecked",
  "-Xlint:deprecation",
  "-Xdiags:verbose"
)

libraryDependencies ++= Seq()

routesGenerator := InjectedRoutesGenerator

unmanagedBase := baseDirectory.value / "custom_lib"
  
lazy val common = (project in file("modules/common"))
  .enablePlugins(PlayJava)
  .settings(commonSettings: _*)
  
lazy val admin = (project in file("modules/admin"))
  .enablePlugins(PlayJava)
  .dependsOn(common)
  .settings(commonSettings: _*)
  
lazy val analytics = (project in file("modules/analytics"))
  .enablePlugins(PlayJava)
  .dependsOn(common)
  
lazy val webide = (project in file("modules/webide"))
  .enablePlugins(PlayJava)
  .dependsOn(common)
  .settings(commonSettings: _*)

lazy val main = (project in file("."))
  .enablePlugins(PlayJava)
  .dependsOn(common, admin, analytics, webide)
  .aggregate(common, admin, analytics, webide)
  .settings(commonSettings: _*)