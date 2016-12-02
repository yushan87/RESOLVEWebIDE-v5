name := "RESOLVEWebIDE"

version := "5.0"

def commonSettings = Seq(
  scalaVersion := "2.11.7", // Scala version to be used in all projects
  routesGenerator := InjectedRoutesGenerator, // Use Injection
  javaFormattingSettingsFilename := "rsrg-format.xml" // Java Formatter
)

// Scala compiler options
scalacOptions ++= Seq(
  "-feature", // Shows warnings in detail in the stdout
  "-language:reflectiveCalls" 
)

// Javac compiler options
javacOptions ++= Seq(
  "-Xlint:unchecked",
  "-Xlint:deprecation",
  "-Xdiags:verbose"
)

// Managed Dependencies
libraryDependencies ++= Seq()

// Unmanaged Dependencies
unmanagedBase := baseDirectory.value / "custom_lib"
  
lazy val common: Project = (project in file("modules/common"))
  .enablePlugins(PlayJava, AutomateHeaderPlugin)
  .settings(commonSettings, aggregateReverseRoutes := Seq(admin, bydesign, webide))
  
lazy val admin = (project in file("modules/admin"))
  .enablePlugins(PlayJava, AutomateHeaderPlugin)
  .dependsOn(common)
  .settings(commonSettings: _*)

lazy val bydesign = (project in file("modules/bydesign"))
  .enablePlugins(PlayJava, AutomateHeaderPlugin)
  .dependsOn(common)
  .settings(commonSettings: _*)
  
lazy val webide = (project in file("modules/webide"))
  .enablePlugins(PlayJava, AutomateHeaderPlugin)
  .dependsOn(common)
  .settings(commonSettings: _*)

lazy val main = (project in file("."))
  .enablePlugins(PlayJava, AutomateHeaderPlugin)
  .dependsOn(common, admin, bydesign, webide)
  .aggregate(common, admin, bydesign, webide)
  .settings(commonSettings: _*)