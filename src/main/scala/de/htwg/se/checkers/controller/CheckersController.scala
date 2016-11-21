package de.htwg.se.checkers.controller

import de.htwg.se.checkers.model.enumeration.Colour
import de.htwg.se.checkers.model.{Piece, PlayField}
import de.htwg.se.checkers.Utils._

class CheckersController(var playfield: PlayField, val rows: Int) {
  assert(playfield.size / 2 > rows, "Wrong number of initializing rows. Maximum allowed: " + ((playfield.size / 2) - 1))

  initPlayfield

  val currentPlayer = 0

  /**
    * logic for initializing the playfield
    *
    * @return
    */
  def initPlayfield: Unit = {


    // set pieces for all player and return a copy of playfield
    for (i <- 0 until rows) {
      for (j <- playfield.board.indices) {
        if ((i + j).isOdd) {
          playfield = playfield.setPiece(i, j, new Piece(Colour.BLACK))
        }
      }
    }

    for (i <- 0 until rows) {
      for (j <- playfield.board.indices) {
        if ((i + 1 + j).isOdd) {
          playfield = playfield.setPiece(playfield.board.length - 1 - i, j, new Piece(Colour.WHITE))
        }
      }
    }
    //playfield
  }

  // API

  // new game

  // move piece

  // get playfield

  // getAvailableMoves

  //
}
