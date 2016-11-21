package de.htwg.se.checkers.model

import org.scalatest._

class PieceSpec extends WordSpec with Matchers {

  "A new default Piece" should {
    val x = 4
    val y = 4
    val color = 0;
    val piece = new Piece(x, y, 0)

    "be not a checkers" in {
      piece.checkers should be(false)
    }

  }
}
