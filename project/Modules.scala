import sbt._
import Keys._

object Modules {

  val rootSettings = Seq(
    name := "BellTower",
    publishArtifact := false,
    publishLocal := false
  )

  val macroSettings = Seq(
    name := "BellTower-macros",
    libraryDependencies <+=  (scalaVersion)("org.scala-lang" % "scala-compiler" % _),
    libraryDependencies ++= Seq("joda-time" % "joda-time" % "2.9.3",
      compilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full))
  )

}