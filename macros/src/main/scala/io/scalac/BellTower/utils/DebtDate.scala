package io.scalac.BellTower.utils

import org.joda.time.DateTime

case class DebtDate(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0)

object DebtDate {
  def convertToDateTime(debtDate: DebtDate): DateTime = {
    new DateTime(debtDate.year, debtDate.month, debtDate.day, debtDate.hour, debtDate.minute)
  }
}
