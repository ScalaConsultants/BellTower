lazy val macros = (project in file("macros"))
  .settings(name := "BellTower-macros")
  .settings(Commons.settings: _*)
  .settings(libraryDependencies ++= Seq("joda-time" % "joda-time" % "2.9.3",
    compilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)))
  .settings(libraryDependencies <+=  (scalaVersion)("org.scala-lang" % "scala-compiler" % _))

lazy val root = (project in file("."))
    .settings(name := "BellTower")
    .settings(Commons.settings: _*)
  .aggregate(macros)
