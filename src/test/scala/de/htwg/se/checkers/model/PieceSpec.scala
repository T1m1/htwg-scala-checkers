package de.htwg.se.checkers.model

import de.htwg.se.checkers.model.enumeration.Colour
import org.scalatest._

class PieceSpec extends WordSpec with Matchers {

  "A new default Piece" should {

    val piece = new Piece(Colour.BLACK)

    "be not a checkers" in {
      piece.checkers should be(false)
    }

  }
}
