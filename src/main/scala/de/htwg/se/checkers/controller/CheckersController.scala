package de.htwg.se.checkers.controller

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import de.htwg.se.checkers.BindingKeys.{NumberOfPlayableRows, PlayfieldSize}
import de.htwg.se.checkers.Utils._
import de.htwg.se.checkers.model.enumeration.Colour
import de.htwg.se.checkers.model.{Piece, PlayField}

class CheckersController()(implicit val bindingModule: BindingModule) extends Injectable {
  // Inject
  val rows = injectOptional[Int](NumberOfPlayableRows) getOrElse 2
  val size = injectOptional[Int](PlayfieldSize) getOrElse 2

  assert(size / 2 > rows, "Wrong number of initializing rows. Maximum allowed: " + ((size / 2) - 1))

  var playfield:PlayField = new PlayField(size)

  initPlayfield

  val currentPlayer = 0

  /**
    * logic for initializing the playfield
    *
    * @return
    */
  def initPlayfield: Unit = {


    // set pieces for all player and return a copy of playfield
    for {
      i <- 0 until rows
      j <- playfield.board.indices
    } if ((i + j).isOdd) {
      playfield = playfield.setPiece(i, j, new Piece(Colour.BLACK))
    } else {
      playfield = playfield.setPiece(playfield.board.length - i - 1, j, new Piece(Colour.WHITE))
    }

  }

  // API

  // new game

  // move piece

  // get playfield

  // getAvailableMoves

  //
}
