package de.htwg.se.checkers.controller

import java.awt.Color

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import de.htwg.se.checkers.BindingKeys.{NumberOfPlayableRows, PlayfieldSize}
import de.htwg.se.checkers.CheckerRules._
import de.htwg.se.checkers.Utils._
import de.htwg.se.checkers.model.api.Coord
import de.htwg.se.checkers.model.enumeration.Colour
import de.htwg.se.checkers.model.{Piece, Playfield}

import scala.collection.immutable.IndexedSeq

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
      i <- playfield.board.indices
      j <- 0 until rows
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

    // unset piece
    playfield = playfield.setPiece(origin, None)

    // set piece
    playfield = playfield.setPiece(target, Some(new Piece(Colour.BLACK)))

  }

  def getPossibleMoves(color: Colour.Value): IndexedSeq[(Int, Int)] = {
    for {
      i <- playfield.board.indices
      j <- 0 until rows
      if getPossibleMoves(new Coord(i, j)).length > 0
    } yield new Coord(i, j)
  }

  def getPossibleMoves(c: Coord): Array[Coord] = {
    // search only if piece is on coordinate
    if (playfield.board(c._1)(c._2).isDefined) {
      recMoves(c._1 - 1, c._2 + 1, false, 1) ++ recMoves(c._1 + 1, c._2 + 1, true, 1)
    } else {
      Array.empty[Coord]
    }
  }

  def outOfBoard(i: Int, j: Int): Boolean = i >= size || j > size || i < 0 || j < 0


  def recMoves(x: Int, y: Int, direction: Boolean, deep: Int): Array[Coord] = {
    if (deep > 2) return Array.empty[Coord]
    if (outOfBoard(x, y)) return Array.empty[Coord]
    // if field free and on board
    if (playfield.board(x)(y).isEmpty) return Array(new Coord(x, y))

    // go left
    if (direction) {
      recMoves(x - 1, y + 1, direction, deep + 1)
    } else {
      recMoves(x + 1, y + 1, direction, deep + 1)
    }
  }

  def isCorrectOrigin(position: Coord): Boolean = playfield(position).exists(_.colour == currentPlayer) && playfield.possibleMoves.contains(position)

  def isCorrectTarget(origin: Coord, target: Coord): Boolean = playfield.listTargets(origin) contains target

  def getTargets(origin: Coord): Set[Coord] = playfield.listTargets(origin)

  // API

  // new game

  // get playfield

  // getAvailableMoves
}
