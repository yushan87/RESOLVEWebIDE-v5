import sbt._
import Keys._
import play.Play.autoImport._
import PlayKeys._

object ApplicationBuild extends Build {

  val appName         = "RESOLVEWebIDE"
  val appVersion      = "v5"

  val appDependencies = Seq(
	javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
	"org.hibernate" % "hibernate-entitymanager" % "4.3.6.Final",
	"com.googlecode.maven-java-formatter-plugin" % "maven-java-formatter-plugin" % "0.4"
  )

  val main = Project(appName, file(".")).enablePlugins(play.PlayJava).settings(
    version := appVersion,
    libraryDependencies ++= appDependencies
  )
  
}