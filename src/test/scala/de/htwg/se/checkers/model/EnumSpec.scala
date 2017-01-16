package de.htwg.se.checkers.model

import de.htwg.se.checkers.model.enumeration.{Colour, Direction}
import org.scalatest._

class EnumSpec extends WordSpec with Matchers {

  "A new black Piece" should {
    val piece = new Piece(Colour.BLACK)

    "should be black" in {
      piece.colour should be(Colour.BLACK)
    }
  }

  "A new white Piece" should {
    val piece = new Piece(Colour.WHITE)

    "should be white" in {
      piece.colour should be(Colour.WHITE)
    }
  }

  "A new ENUM of direction" should {

    "contain the correct String" in {
      Direction.withName("UP")
      Direction.DOWN.toString should be("DOWN")
      Direction.UP.toString should be("UP")
      Direction.RIGHT.toString should be("RIGHT")
      Direction.LEFT.toString should be("LEFT")
    }
  }


}
