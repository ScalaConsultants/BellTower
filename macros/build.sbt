name := "BellTower-macros"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"

organization := "io.scalac"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-compiler" % "2.11.7",
  "joda-time" % "joda-time" % "2.9.3"
)

val paradiseVersion = "2.1.0-M5"

addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)