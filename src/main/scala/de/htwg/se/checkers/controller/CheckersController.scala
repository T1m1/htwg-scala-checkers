package de.htwg.se.checkers.controller

import de.htwg.se.checkers.model.enumeration.Colour
import de.htwg.se.checkers.model.{Piece, Playfield, Coord}
import de.htwg.se.checkers.Utils._
import de.htwg.se.checkers.CheckerRules._

class CheckersController(var playfield: Playfield, val rows: Int) {
  assert(playfield.ensureCorrectRows(rows), "Wrong number of initializing rows. Maximum allowed: " + ((playfield.size / 2) - 1))

  initPlayfield

  val currentPlayer = 0

  /**
    * logic for initializing the playfield
    *
    * @return
    */
  def initPlayfield: Unit = {

    // set pieces for all player
    for {
      i <- 0 until rows
      j <- playfield.board.indices
    } if ((i + j).isOdd) {
      playfield = playfield.setPiece(new Coord(i, j), new Piece(Colour.BLACK))
    } else {
      playfield = playfield.setPiece(new Coord(playfield.board.length - i - 1, j), new Piece(Colour.WHITE))
    }

  }

  // API

  // new game

  // move piece

  // get playfield

  // getAvailableMoves

  //
}
