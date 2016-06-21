# BellTower - easy technical debt management

**BellTower** is a project that allows the developer to annotate Scala classes, traits, objects and methods with TechnicalDebt
annotation and follow with their refactoring.

## Usage

In order to use the BellTower project add it to `libraryDependencies` in your `build.sbt` file:

```sbt
libraryDependencies ++= Seq("io.scalac" %% "belltower-macros" % "0.1.1-SNAPSHOT")
```

Besides adding the BellTower library the developer needs to enable a plugin that allows the compiler to expand macros definitions
(add the following line to the `build.sbt` file:

```sbt
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
```

Currently, BellTower supports only Scala 2.11.

In order to annotate an entity as deprecated use Scala annotation syntax, like so:

```scala
import io.scalac.BellTower.annotations.TechnicalDebt
import org.joda.time.DateTime

object Test {

  @TechnicalDebt(new DateTime(2016,5,10), "Ooops!")
  case class Test(name: String)

}
```

If you try to compile the example above with `sbt compile` you should receive the following warning:

```bash
[warn] The class Test is marked as technical debt as of 2016-05-10 00:00. Please, reconsider refactoring it.
[warn] The technical debt description for the class Test: Ooops!
```

## Publishing the project locally

In order to test the project you may need to publish it locally. In order to do that go to project root directory and type into console:

```
sbt +publishLocal
```

After the process has finished, the project should be available at your home folder in `.ivy2/local` folder.

## Technical debt annotation

The TechnicalDebt annotation takes three parameters:
- The date since which the entity shuld be refactored
- The description of a technical debt issue
- The URI to a ticket (only available when inspecting the code)


The tests are missing.