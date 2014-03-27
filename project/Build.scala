import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "RESOLVEWebIDE"
  val appVersion      = "v5"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaJpa,
    "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

  playJavaSettings
}