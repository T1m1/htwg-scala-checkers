package de.htwg.se.checkers.controller

import de.htwg.se.checkers.controller.command.{GameStatus, SetPiece}
import de.htwg.se.checkers.model.enumeration.Colour
import org.scalamock.scalatest.MockFactory
import org.scalatest._

class CheckersControllerSpecGame extends WordSpec with Matchers with MockFactory {
  val playerOne = Colour.BLACK
  val playerTwo = Colour.WHITE

  "A new Controller with CheckersTestConfiguration injection" should {
    val ctr = new CheckersController()(CheckersTestConfiguration)
    ctr.movePiece((1, 2), (0, 3))
    ctr.movePiece((0, 5), (1, 4))
    val status = ctr.movePiece((3, 2), (2, 3))

    "get possible moves should contain 1 element" in {
      status should be(true)
      ctr.getPossibleMoves.length should be(1)
    }

    "get possible moves should contain (1,4),(3,2" in {
      ctr.getPossibleMoves should contain((1, 4), (3, 2))
    }
    "get possible pieces" in {
      ctr.getPossiblePieces should contain((1, 4))
    }

    "get possible targets for (0,5)" in {
      ctr.getPossibleTargets((1, 4)) should contain((3, 2))
    }
  }

  "capture piece" should {
    val ctr = new CheckersController()(CheckersTestConfiguration)
    ctr.movePiece((1, 2), (0, 3))
    ctr.movePiece((0, 5), (1, 4))
    ctr.movePiece((3, 2), (2, 3))
    var status = ctr.movePiece((1, 4), (3, 2))

    "get possible moves should contain 1 element" in {
      status should be(true)
    }

    "get no targets" in {
      ctr.getPossibleTargets((1, 0)).length should be(0)
    }

    "hande command" in {
      /*
        def handleCommand(command: Command): GameState = {
    command match {
      case SetPiece(start, end) =>
        movePiece(start, end); getState
      case GameStatus() => getState
    }
       */
      ctr.handleCommand(SetPiece((1, 0), (1, 0)))
      ctr.handleCommand(GameStatus())
    }
  }

}