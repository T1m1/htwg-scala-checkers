package de.htwg.se.checkers.model

import org.scalatest._

class PieceSpec extends FlatSpec with Matchers {


  "A new default Piece" should "be not a checkers" in {
    new Piece(1, 2).checkers should be(false)
  }
}
