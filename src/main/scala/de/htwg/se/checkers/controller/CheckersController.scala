package de.htwg.se.checkers.controller

import de.htwg.se.checkers.model.enumeration.Colour
import de.htwg.se.checkers.model.{Piece, PlayField}

class CheckersController(var playfield: PlayField, val rows: Int) {

  initPlayfield

  val currentPlayer = 0

  /**
    * logic for initializing the playfield
    * @return
    */
  def initPlayfield: Unit = {


    // set pieces for all player and return a copy of playfield
    playfield = playfield.setPiece(0, 0, new Piece(Colour.BLACK))
    playfield = playfield.setPiece(0, 2, new Piece(Colour.BLACK))
    playfield = playfield.setPiece(0, 4, new Piece(Colour.BLACK))
    playfield = playfield.setPiece(0, 6, new Piece(Colour.BLACK))
    playfield = playfield.setPiece(1, 1, new Piece(Colour.BLACK))
    playfield = playfield.setPiece(1, 3, new Piece(Colour.BLACK))
    playfield = playfield.setPiece(1, 5, new Piece(Colour.BLACK))
    playfield = playfield.setPiece(1, 7, new Piece(Colour.BLACK))
    //playfield
  }

  // API

  // new game

  // move piece

  // get playfield

  // getAvailableMoves

  //
}
