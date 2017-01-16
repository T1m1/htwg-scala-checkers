package de.htwg.se.checkers.model

import de.htwg.se.checkers.model.enumeration.Colour
import org.scalatest._

class FieldSpec extends WordSpec with Matchers {

  "A new black default Piece" should {

    val x = 0
    val y = 0
    val field = new Field(x, y)

    // call unapply to get 100% coverage for case classes
    Field.unapply(field)

    "be on position x=" + x + " y= " + y in {
      field.x should be(x)
      field.y should be(y)
    }

    "should be playable" in {
      field.isPlayable should be(true)
    }
  }
}
