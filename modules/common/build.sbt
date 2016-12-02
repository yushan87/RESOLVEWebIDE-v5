import de.heikoseeberger.sbtheader.HeaderPattern

name := "RESOLVEWebIDE-common"

version := "1.0"

// Scala compiler options
scalacOptions ++= Seq(
  "-feature" // Shows warnings in detail in the stdout
)

// Javac compiler options
javacOptions ++= Seq(
  "-Xlint:unchecked",
  "-Xlint:deprecation",
  "-Xdiags:verbose"
)

// Managed Dependencies
libraryDependencies ++= Seq(
  // Play Framework
  javaJdbc,
  javaJpa,
  cache,
  filters,
  javaWs,
  "com.typesafe.play" %% "play-mailer" % "5.0.0",

  // Database
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  "mysql" % "mysql-connector-java" % "5.1.39"
)

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