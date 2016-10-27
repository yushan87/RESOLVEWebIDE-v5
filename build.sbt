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
  
lazy val common: Project = (project in file("modules/common"))
  .enablePlugins(PlayJava)
  .settings(commonSettings, aggregateReverseRoutes := Seq(admin, bydesign, webide))
  
lazy val admin = (project in file("modules/admin"))
  .enablePlugins(PlayJava)
  .dependsOn(common)
  .settings(commonSettings: _*)

lazy val bydesign = (project in file("modules/bydesign"))
  .enablePlugins(PlayJava)
  .dependsOn(common)
  .settings(commonSettings: _*)
  
lazy val webide = (project in file("modules/webide"))
  .enablePlugins(PlayJava)
  .dependsOn(common)
  .settings(commonSettings: _*)

lazy val main = (project in file("."))
  .enablePlugins(PlayJava)
  .dependsOn(common, admin, bydesign, webide)
  .aggregate(common, admin, bydesign, webide)
  .settings(commonSettings: _*)