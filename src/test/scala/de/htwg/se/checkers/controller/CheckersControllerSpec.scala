package de.htwg.se.checkers.controller

import de.htwg.se.checkers.model.enumeration.Colour
import org.scalamock.scalatest.MockFactory
import org.scalatest._

class CheckersControllerSpec extends WordSpec with Matchers with MockFactory {

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
      ctr.playerTwo should be(Colour.WHITE)
    }

    "In the initial game state" should {
      val currentGameState = ctr.getState

      "player one should come first" in {
        currentGameState.currentPlayer should be(Colour.BLACK)
      }
      "piece at (0,1) should be Black" in {
        currentGameState.field.board(0)(1).get.colour should be(Colour.WHITE)
      }
    }
  }
}
