name := "RESOLVEWebIDE"

version := "5.0"

def commonSettings = Seq(
  // Scala version to be used in all projects
  scalaVersion := "2.12.6",
  // Use Injection
  routesGenerator := InjectedRoutesGenerator,
  // Java Formatter
  javaFormattingSettingsFilename := "rsrg-format.xml",
  // License Headers
  headerMappings := headerMappings.value + (HeaderFileType.java -> HeaderCommentStyle.cStyleBlockComment),
  headerLicense := Some(HeaderLicense.Custom(
    """|---------------------------------
       |Copyright (c) 2018
       |RESOLVE Software Research Group
       |School of Computing
       |Clemson University
       |All rights reserved.
       |---------------------------------
       |This file is subject to the terms and conditions defined in
       |file 'LICENSE.txt', which is part of this source code package.""".stripMargin
  ))
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
libraryDependencies ++= Seq(
  guice,
  "com.google.code.findbugs" % "jsr305" % "1.3.9"
)

// Unmanaged Dependencies
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
  .enablePlugins(PlayJava, AutomateHeaderPlugin)
  .dependsOn(common, admin, bydesign, webide)
  .aggregate(common, admin, bydesign, webide)
  .settings(commonSettings: _*)