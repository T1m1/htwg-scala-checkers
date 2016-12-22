package de.htwg.se.checkers.controller

import java.awt.Color

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import de.htwg.se.checkers.BindingKeys.{NumberOfPlayableRows, PlayfieldSize}
import de.htwg.se.checkers.CheckerRules._
import de.htwg.se.checkers.Utils._
import de.htwg.se.checkers.model.api.{Coord, Move}
import de.htwg.se.checkers.model.enumeration.{Colour, Direction}
import de.htwg.se.checkers.model.{Piece, Playfield}

import scala.collection.immutable.IndexedSeq

class CheckersController()(implicit val bindingModule: BindingModule) extends Injectable {
  // Inject
  val rows = injectOptional[Int](NumberOfPlayableRows) getOrElse 2
  val size = injectOptional[Int](PlayfieldSize) getOrElse 2

  var playfield: Playfield = new Playfield(size)

  assert(playfield.ensureCorrectRows(rows), "Wrong number of initializing rows. Maximum allowed: " + ((size / 2) - 1))


  initPlayfield

  var currentPlayer = Colour.BLACK

  /**
    * logic for initializing the playfield
    *
    * @return
    */
  def initPlayfield: Unit = {
    // set pieces for all player
    for {
      i <- playfield.board.indices
      j <- 0 until rows
    } if ((i + j).isOdd) {
      playfield = playfield.setPiece((i, j), Some(new Piece(Colour.BLACK)))
    } else {
      playfield = playfield.setPiece((i, playfield.board.length - j - 1), Some(new Piece(Colour.WHITE)))
    }
  }

  // not immutable!
  def nextPlayer: Unit = if (currentPlayer.equals(Colour.BLACK)) currentPlayer = Colour.WHITE else currentPlayer = Colour.BLACK

  def movePiece(origin: Coord, target: Coord): Unit = {
    // check if origin is correct
    //    assume(isCorrectOrigin(origin)) TODO @Steffen please fix your code
    // check if target is correct
    //    assume(getTargets(origin) contains target) TODO @Steffen please fix your code
    // unset piece
    playfield = playfield.setPiece(origin, None)

    //TODO check if piece is capture -> remove captured element

    // set piece
    playfield = playfield.setPiece(target, Some(new Piece(currentPlayer)))
    nextPlayer
  }

  def getPossiblePieces(color: Colour.Value): IndexedSeq[(Int, Int)] = {
    for {
      i <- playfield.board.indices
      j <- playfield.board(i).indices
      if getPossibleMoves(new Coord(i, j), color).length > 0
    } yield new Coord(i, j)
  }

  def getPossibleMoves(color: Colour.Value): IndexedSeq[((Int, Int), (Int, Int))] = {
    for {
      i <- playfield.board.indices
      j <- playfield.board(i).indices
      moves <- getPossibleMoves(new Coord(i, j), color)
    } yield new Move((i, j), moves)
  }

  def getPossibleMoves(c: Coord, color: Colour.Value): Array[Coord] = {
    // search only if piece is on coordinate
    if (playfield.board(c._1)(c._2).isDefined && playfield.board(c._1)(c._2).get.colour.equals(color)) {
      if (color.equals(Colour.BLACK)) {
        recMoves(c._1 - 1, c._2 + 1, Direction.LEFT, 1, color) ++ recMoves(c._1 + 1, c._2 + 1, Direction.RIGHT, 1, color)
      } else {
        recMoves(c._1 - 1, c._2 - 1, Direction.LEFT, 1, color) ++ recMoves(c._1 + 1, c._2 - 1, Direction.RIGHT, 1, color)
      }
    } else {
      Array.empty[Coord]
    }
  }

  def outOfBoard(i: Int, j: Int): Boolean = i >= size || j >= size || i < 0 || j < 0

  def newPositionX(x: Integer, direction: Direction.Value): Integer = if (direction.equals(Direction.LEFT)) x - 1 else x + 1

  def newPositionY(y: Integer, colour: Colour.Value): Integer = if (colour.equals(Colour.BLACK)) y + 1 else y - 1

  def recMoves(x: Int, y: Int, direction: Direction.Value, deep: Int, colour: Colour.Value): Array[Coord] = {
    if (deep > 2) return Array.empty[Coord]
    if (outOfBoard(x, y)) return Array.empty[Coord]
    // if field free and on board
    if (playfield.board(x)(y).isEmpty) return Array(new Coord(x, y))
    // if same color at position, it is not possible to move the piece
    if (playfield.board(x)(y).isDefined && playfield.board(x)(y).get.colour.equals(colour)) return Array.empty[Coord]

    val newX = newPositionX(x, direction)
    val newY = newPositionY(y, colour)

    // recursiveMove
    recMoves(newX, newY, direction, deep + 1, colour)
  }

  def isCorrectOrigin(position: Coord): Boolean = playfield(position).exists(_.colour == currentPlayer) && playfield.possibleMoves.contains(position)

  def isCorrectTarget(origin: Coord, target: Coord): Boolean = playfield.listTargets(origin) contains target

  def getTargets(origin: Coord): Set[Coord] = playfield.listTargets(origin)
}
