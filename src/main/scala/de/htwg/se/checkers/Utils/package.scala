package de.htwg.se.checkers.Utils

import scala.language.implicitConversions

import de.htwg.se.checkers.model.Playfield
import play.api.libs.json._

object Utils {

  implicit class BetterInt(i: Int) {
    def isEven: Boolean = i % 2 == 0

    def isOdd: Boolean = i % 2 == 1
  }

}

object CheckerRules {

  implicit class Rule(field: Playfield) {

    // There must be at least one empty row on the field at the beginning of the game
    def ensureCorrectRows(rows: Int): Boolean = rows > 0 && field.size / 2 > rows

  }

}

object EnumUtils {

  def enumReads[E <: Enumeration](enum: E): Reads[E#Value] =
    new Reads[E#Value] {
      def reads(json: JsValue): JsResult[E#Value] = json match {
        case JsString(s) => {
          try {
            JsSuccess(enum.withName(s))
          } catch {
            case _: NoSuchElementException =>
              JsError(s"Enumeration expected of type: '${enum.getClass}', but it does not appear to contain the value: '$s'")
          }
        }
        case _ => JsError("String value expected")
      }
    }

  implicit def enumWrites[E <: Enumeration]: Writes[E#Value] =
    new Writes[E#Value] {
      def writes(v: E#Value): JsValue = JsString(v.toString)
    }

  implicit def enumFormat[E <: Enumeration](enum: E): Format[E#Value] = {
    Format(enumReads(enum), enumWrites)
  }
}
