name := "BellTower"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"

organization := "io.scalac"

lazy val macros = project

lazy val root = (project in file("."))
  .aggregate(macros)

val paradiseVersion = "2.1.0-M5"

addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)