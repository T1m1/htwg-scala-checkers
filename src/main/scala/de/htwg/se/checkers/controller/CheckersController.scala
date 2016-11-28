package de.htwg.se.checkers.controller

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import de.htwg.se.checkers.BindingKeys.{NumberOfPlayableRows, PlayfieldSize}
import de.htwg.se.checkers.CheckerRules._
import de.htwg.se.checkers.Utils._
import de.htwg.se.checkers.model.api.Coord
import de.htwg.se.checkers.model.enumeration.Colour
import de.htwg.se.checkers.model.{Piece, Playfield}

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
      playfield = playfield.setPiece((i, j), Some(new Piece(Colour.BLACK)))
    } else {
      playfield = playfield.setPiece((playfield.board.length - i - 1, j), Some(new Piece(Colour.WHITE)))
    }

  }

  def movePiece(origin: Coord, target: Coord): Unit = {
    // check if origin is correct
    assume(isCorrectOrigin(origin))

    // check if target is correct
    assume(getTargets(origin) contains target)

    // unset all pieces
    playfield = playfield.setPiece(origin, None)

    // set piece
    playfield = playfield.setPiece(target, Some(new Piece(Colour.BLACK)))

  }

  def isCorrectOrigin(position: Coord): Boolean = playfield(position).exists(_.colour == currentPlayer) && playfield.possibleMoves.contains(position)

  def isCorrectTarget(origin: Coord, target: Coord): Boolean = playfield.listTargets(origin) contains target

  def getTargets(origin: Coord): Set[Coord] = playfield.listTargets(origin)

  // API

  // new game

  // move piece

  // get playfield

  // getAvailableMoves

  //
}
