name := "RESOLVEWebIDE-webide"

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

libraryDependencies ++= Seq(
  // Deadbolt 2 (Authentication/Restriction)
  "be.objectify" %% "deadbolt-java" % "2.5.0"
)

routesGenerator := InjectedRoutesGenerator