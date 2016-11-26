import de.heikoseeberger.sbtheader.HeaderPattern

name := "RESOLVEWebIDE"

version := "5.0"

def commonSettings = Seq(
  scalaVersion := "2.11.7"
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

// Use Injection
routesGenerator := InjectedRoutesGenerator

// License Headers
headers := headers.value ++ Map(
  "java" -> (
    HeaderPattern.cStyleBlockComment,
    """|/**
       | * ---------------------------------
       | * Copyright (c) 2016
       | * RESOLVE Software Research Group
       | * School of Computing
       | * Clemson University
       | * All rights reserved.
       | * ---------------------------------
       | * This file is subject to the terms and conditions defined in
       | * file 'LICENSE.txt', which is part of this source code package.
       | */
       |""".stripMargin
  )
)
  
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