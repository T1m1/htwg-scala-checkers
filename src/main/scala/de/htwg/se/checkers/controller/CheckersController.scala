package de.htwg.se.checkers.controller

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import de.htwg.se.checkers.BindingKeys.{NumberOfPlayableRows, PlayfieldSize}
import de.htwg.se.checkers.CheckerRules._
import de.htwg.se.checkers.Utils._
import de.htwg.se.checkers.model.enumeration.Colour
import de.htwg.se.checkers.model.{Coord, Piece, Playfield}

class CheckersController()(implicit val bindingModule: BindingModule) extends Injectable {
  // Inject
  val rows = injectOptional[Int](NumberOfPlayableRows) getOrElse 2
  val size = injectOptional[Int](PlayfieldSize) getOrElse 2

  var playfield: Playfield = new Playfield(size)

  assert(playfield.ensureCorrectRows(rows), "Wrong number of initializing rows. Maximum allowed: " + ((size / 2) - 1))


  initPlayfield

  val currentPlayer = Colour.BLACK

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
      playfield = playfield.setPiece(new Coord(i, j), Some(new Piece(Colour.BLACK)))
    } else {
      playfield = playfield.setPiece(new Coord(playfield.board.length - i - 1, j), Some(new Piece(Colour.WHITE)))
    }

  }

  def movePiece(origin: Coord, destiny: Coord): Unit = {
    // check if move is possible
    require(isCorrectOrigin(origin))

    // unset all pieces
    playfield = playfield.setPiece(origin, None)

    // set piece
    playfield = playfield.setPiece(destiny, Some(new Piece(Colour.BLACK))) // TODO add logic


  }

  def isCorrectOrigin(position: Coord): Boolean = playfield(position).exists(_.colour == currentPlayer) &&
    (playfield.possibleMoves contains position)


  // API

  // new game

  // move piece

  // get playfield

  // getAvailableMoves

  //
}
