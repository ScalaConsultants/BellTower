import sbt._
import Keys._

object Commons {

  val vr = "0.1.2-SNAPSHOT"
  val scalaVr = "2.11.8"
  val crossVr = Seq("2.11.6", "2.11.7", "2.11.8")

  val settings = Seq(
    version := vr,
    scalaVersion := scalaVr,
    crossScalaVersions := crossVr,
    organization := "io.scalac",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Ywarn-value-discard"),
    autoCompilerPlugins := true
  )

}
