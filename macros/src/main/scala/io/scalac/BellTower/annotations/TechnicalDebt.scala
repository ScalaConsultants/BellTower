package io.scalac.BellTower.annotations

import java.net.URI

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

/** This class invokes macro implementation and sets required parameters
  *
  * @param warnAfter DateTime class containing the date as of which the entity is considered as technical debt
  * @param desc a short description of technical debt
  * @param ticket a URI (optional) to a ticket (Jira, RedMine)
  */
@compileTimeOnly("Enable Macro Paradise for Expansion of Annotations via Macros.")
class TechnicalDebt(warnAfter: DateTime, desc: String, ticket: Option[URI] = None) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro TechnicalDebt.impl
}

object TechnicalDebt {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    val conditions = c.prefix.tree match {
      case q"""new TechnicalDebt(...$paramss)""" => paramss
      case _ => c.warning(NoPosition, "Interesting")
    }

    val dtObj: DateTime = conditions match {
      case List(List(AssignOrNamedArg(_, Apply(_, List(Literal(Constant(y)), Literal(Constant(m)), Literal(Constant(d))))), _*)) =>
        new DateTime(y.asInstanceOf[Int], m.asInstanceOf[Int], d.asInstanceOf[Int], 0, 0)
      case List(List(AssignOrNamedArg(_, Apply(_, List(Literal(Constant(y)), Literal(Constant(m)), Literal(Constant(d)), Literal(Constant(h))))), _*)) =>
        new DateTime(y.asInstanceOf[Int], m.asInstanceOf[Int], d.asInstanceOf[Int], h.asInstanceOf[Int], 0)
      case List(List(AssignOrNamedArg(_, Apply(_, List(Literal(Constant(y)), Literal(Constant(m)), Literal(Constant(d)), Literal(Constant(h)), Literal(Constant(mn))))), _*)) =>
        new DateTime(y.asInstanceOf[Int], m.asInstanceOf[Int], d.asInstanceOf[Int], h.asInstanceOf[Int], mn.asInstanceOf[Int])
      case List(List(Apply(_, List(Literal(Constant(y)), Literal(Constant(m)), Literal(Constant(d)))), _*)) =>
        new DateTime(y.asInstanceOf[Int], m.asInstanceOf[Int], d.asInstanceOf[Int], 0, 0)
      case List(List(Apply(_, List(Literal(Constant(y)), Literal(Constant(m)), Literal(Constant(d)), Literal(Constant(h)))), _*)) =>
        new DateTime(y.asInstanceOf[Int], m.asInstanceOf[Int], d.asInstanceOf[Int], h.asInstanceOf[Int], 0)
      case List(List(Apply(_, List(Literal(Constant(y)), Literal(Constant(m)), Literal(Constant(d)), Literal(Constant(h)), Literal(Constant(mn)))), _*)) =>
        new DateTime(y.asInstanceOf[Int], m.asInstanceOf[Int], d.asInstanceOf[Int], h.asInstanceOf[Int], mn.asInstanceOf[Int])
      case _ =>
        val now = DateTime.now()
        new DateTime(now.getYear, now.getMonthOfYear, 1, 0, 0)
    }

    val desc: String = conditions match {
      case List(List(_, Literal(Constant(txt)), _*)) => txt.toString
      case List(List(_, AssignOrNamedArg(_, Literal(Constant(txt))), _*)) => txt.toString
      case _ => "Custom description."
    }

    val ticket: Option[java.net.URI] = conditions match {
      case List(List(_, _, Apply(_, List(Apply(_, List(Literal(Constant(uri)))))))) => Some(new java.net.URI(uri.toString))
      case List(List(_, _, AssignOrNamedArg(Ident(TermName("ticket")), Apply(_, List(Apply(_, List(Literal(Constant(uri))))))))) => Some(new java.net.URI(uri.toString))
      case _ => None
    }

    def issueWarnings(date: DateTime, name: AnyRef): Unit = {
      val dtFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")
      if (date.isBefore(DateTime.now()))
      {
        c.warning(NoPosition, s"The class $name is marked as technical debt as of ${dtFormat.print(dtObj)}. Please, reconsider refactoring it.")
        c.warning(NoPosition, s"The technical debt description for the class $name: $desc")
      }
    }

    val result = {
      annottees.map(_.tree).toList match {
        case q"$mods class $tpname[..$tparams] $ctorMods(...$paramss) extends ..$parents { $self => ..$stats }" :: Nil => {
          issueWarnings(dtObj, tpname)
          q"$mods class $tpname[..$tparams] $ctorMods(...$paramss) extends ..$parents { $self => ..$stats }"
        }
        case q"$mods object $tname extends { ..$earlydefns } with ..$parents { $self => ..$body }" :: Nil => {
          issueWarnings(dtObj, tname)
          q"$mods object $tname extends { ..$earlydefns } with ..$parents { $self => ..$body }"
        }
        case q"$mods trait $tpname[..$tparams] extends { ..$earlydefns } with ..$parents { $self => ..$stats }" :: Nil => {
          issueWarnings(dtObj, tpname)
          q"$mods trait $tpname[..$tparams] extends { ..$earlydefns } with ..$parents { $self => ..$stats }"
        }
        case q"$mods def $tname[..$tparams](...$paramss): $tpt = $expr" :: Nil => {
          issueWarnings(dtObj, tname)
          q"$mods def $tname[..$tparams](...$paramss): $tpt = $expr"
        }
        case _ => c.abort(c.enclosingPosition, "Could not recognize Scala entity, please report a bug to Github.")
      }
    }

    c.Expr[Any](result)
  }
}
