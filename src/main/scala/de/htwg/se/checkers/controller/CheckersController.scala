package de.htwg.se.checkers.controller

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import de.htwg.se.checkers.BindingKeys.{NumberOfPlayableRows, PlayfieldSize}
import de.htwg.se.checkers.Utils._
import de.htwg.se.checkers.model.enumeration.Colour
import de.htwg.se.checkers.model.{Piece, Playfield, Coord}
import de.htwg.se.checkers.Utils._
import de.htwg.se.checkers.CheckerRules._

class CheckersController(var playfield: Playfield, val rows: Int) {
  assert(playfield.ensureCorrectRows(rows), "Wrong number of initializing rows. Maximum allowed: " + ((playfield.size / 2) - 1))
class CheckersController()(implicit val bindingModule: BindingModule) extends Injectable {
  // Inject
  val rows = injectOptional[Int](NumberOfPlayableRows) getOrElse 2
  val size = injectOptional[Int](PlayfieldSize) getOrElse 2

  assert(playfield.ensureCorrectRows(rows), "Wrong number of initializing rows. Maximum allowed: " + ((playfield.size / 2) - 1))

  var playfield:PlayField = new PlayField(size)

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
    // check if move is poosible

    // unset all pieces
    playfield = playfield.setPiece(origin, None)

    // set piece
    playfield = playfield.setPiece(destiny, Some(new Piece(Colour.BLACK))) // TODO add logic


  }

  def isCorrectOrigin(origin: Coord, field: Playfield): Boolean = isCurrentPlayer(origin, field) && hasPossibleMoves(origin, field)

  def isCurrentPlayer(origin: Coord, field: Playfield): Boolean = field.board(origin.x)(origin.y).exists(_.colour == currentPlayer)

  def hasPossibleMoves(origin: Coord, field: Playfield): Boolean = field.possibleMoves contains origin

  // API

  // new game

  // move piece

  // get playfield

  // getAvailableMoves

  //
}
