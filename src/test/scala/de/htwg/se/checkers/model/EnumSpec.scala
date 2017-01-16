package de.htwg.se.checkers.model

import de.htwg.se.checkers.model.enumeration.Colour
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
}
