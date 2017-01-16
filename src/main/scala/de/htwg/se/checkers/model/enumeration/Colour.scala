package de.htwg.se.checkers.model.enumeration

import play.api.libs.json._

object Colour extends Enumeration {

  type Colour = Value
  val BLACK = Value("Black")
  val WHITE = Value("White")

  implicit val ColourFormat = new Format[Colour] {
    def reads(json: JsValue) = JsSuccess(Colour.withName(json.as[String]))

    def writes(myEnum: Colour) = JsString(myEnum.toString)
  }
}
