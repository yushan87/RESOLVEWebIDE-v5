import de.heikoseeberger.sbtheader.HeaderPattern

name := "RESOLVEWebIDE-admin"

version := "1.0"

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