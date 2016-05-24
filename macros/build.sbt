name := "BellTower-macros"

version := "0.1.1-SNAPSHOT"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.6", "2.11.7", "2.11.8")

organization := "io.scalac"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Ywarn-value-discard")

libraryDependencies += "joda-time" % "joda-time" % "2.9.3"
libraryDependencies <+=  (scalaVersion)("org.scala-lang" % "scala-compiler" % _)


addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)