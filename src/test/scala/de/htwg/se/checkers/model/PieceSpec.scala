package de.htwg.se.checkers.model

import de.htwg.se.checkers.model.enumeration.Colour
import org.scalatest._

class PieceSpec extends WordSpec with Matchers {

  "A new black default Piece" should {

    val piece = new Piece(Colour.BLACK)

    // call unapply to get 100% coverage for case classes
    Piece.unapply(piece)

    "be not a checkers" in {
      piece.checkers should be(false)
    }

    "should be black" in {
      piece.colour should be(Colour.BLACK)
    }
  }
}
