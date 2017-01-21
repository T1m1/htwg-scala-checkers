package de.htwg.se.checkers.model.enumeration

import de.htwg.se.checkers.Utils.EnumUtils
import play.api.libs.json._

object Colour extends Enumeration {

  type Colour = Value
  val BLACK = Value("Black")
  val WHITE = Value("White")

  implicit def enumReads: Reads[Colour] = EnumUtils.enumReads(Colour)

  implicit def enumWrites: Writes[Colour] = EnumUtils.enumWrites

}
