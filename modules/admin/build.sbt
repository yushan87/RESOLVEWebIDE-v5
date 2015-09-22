name := "RESOLVEWebIDE-admin"

version := "1.0"

scalaVersion := "2.11.7"

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

resolvers ++= Seq(
  "play-authenticate (snapshot)" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

routesGenerator := InjectedRoutesGenerator