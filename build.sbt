lazy val macros = (project in file("macros"))
  .settings(Commons.settings: _*)
  .settings(Modules.macroSettings: _*)

lazy val root = (project in file("."))
  .settings(Commons.settings: _*)
  .settings(Modules.rootSettings: _*)
  .aggregate(macros)
