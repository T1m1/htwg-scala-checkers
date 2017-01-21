package de.htwg.se.checkers.controller

import java.util

import CheckersConfiguration
import de.htwg.se.checkers.model.Piece
import de.htwg.se.checkers.model.enumeration.Colour
import org.scalatest._

class CheckersControllerSpec extends WordSpec with Matchers {


  "A new Controller with CheckersTestConfiguration injection" should {
    val ctr = new CheckersController()(CheckersTestConfiguration)

    "have 3 playable rows" in {
      ctr.rows should be(3)
    }
    "have a size of 8" in {
      ctr.size should be(8)
    }

    "the first player should be BLACK" in {
      ctr.playerOne should be(Colour.BLACK)
    }

    "the second player should be WHITE" in {
      ctr.playerOne should be(Colour.WHITE)
    }


  }

}
