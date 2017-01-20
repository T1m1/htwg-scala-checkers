package de.htwg.se.checkers.controller

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import de.htwg.se.checkers.BindingKeys.{NumberOfPlayableRows, PlayfieldSize}
import de.htwg.se.checkers.controller.command._
import de.htwg.se.checkers.Utils.Utils._
import de.htwg.se.checkers.Utils.CheckerRules._
import de.htwg.se.checkers.model.api._
import de.htwg.se.checkers.model.enumeration.{Colour, Direction}
import de.htwg.se.checkers.model.{GameState, Piece, Playfield}

import scala.collection.immutable.IndexedSeq

class CheckersController()(implicit val bindingModule: BindingModule) extends Injectable {

  def getState: GameState = GameState(playfield, currentPlayer)

  // Inject
  val rows: Int = injectOptional[Int](NumberOfPlayableRows) getOrElse 2
  val size: Int = injectOptional[Int](PlayfieldSize) getOrElse 2

  var playfield: Playfield = new Playfield(size)

  assert(playfield.ensureCorrectRows(rows), "Wrong number of initializing rows. Maximum allowed: " + ((size / 2) - 1))

  initPlayfield

  var currentPlayer = Colour.BLACK

  /**
    * logic for initializing the playfield
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

  def movePiece(origin: Coord, target: Coord): Boolean = {
    // assert correct move
    if (!isCorrectMove(origin, target)) return false
    assume(isCorrectMove(origin, target))

    // unset piece
    playfield = playfield.setPiece(origin, None)

    //check if piece is capture -> remove captured element
    if ((math.abs(origin._1 - target._1) == 2) && (Math.abs(origin._2 - target._2) == 2)) {
      val x = if (origin._1 < target._1) origin._1 + 1 else origin._1 - 1
      val y = if (target._2 > origin._2) target._2 - 1 else target._2 + 1
      playfield = playfield.setPiece(new Coord(x, y), None)
    }

    // set piece
    playfield = playfield.setPiece(target, Some(new Piece(currentPlayer)))
    nextPlayer
    true
  }

  def getPossiblePieces: IndexedSeq[(Int, Int)] = {
    for {
      i <- playfield.board.indices
      j <- playfield.board(i).indices
      if calculatePossibleMoves(new Coord(i, j)).length > 0
    } yield new Coord(i, j)
  }

  def getPossibleMoves: IndexedSeq[((Int, Int), (Int, Int))] = {
    val moves = for {
      i <- playfield.board.indices
      j <- playfield.board(i).indices
      moves <- calculatePossibleMoves(new Coord(i, j))
    } yield new MoveCheck((i, j), moves._1, moves._2)

    val captures = for {
      i <- moves
      if i._3.equals(true)
    } yield new Move(i._1, i._2)

    if (captures.nonEmpty) {
      captures
    } else {
      // transform to move
      for (i <- moves) yield new Move(i._1, i._2)
    }
  }

  def getPossibleTargets(c: Coord): Seq[Coord] = {
    val moves = calculatePossibleMoves(c)

    val captures = for {
      i <- moves
      if i._2.equals(true)
    } yield new Coord(i._1._1, i._1._2)

    if (captures.nonEmpty) {
      captures
    } else {
      for (i <- moves) yield new Coord(i._1._1, i._1._2)
    }
  }

  private def calculatePossibleMoves(c: Coord): Array[CoordStep] = {
    if (playfield.board(c._1)(c._2).isEmpty || playfield.board(c._1)(c._2).get.colour != currentPlayer) {
      return Array.empty[CoordStep]
    }
    // search only if piece is on coordinate
    if (playfield.board(c._1)(c._2).isDefined && playfield.board(c._1)(c._2).get.colour.equals(currentPlayer)) {
      if (currentPlayer.equals(Colour.BLACK)) {
        recMoves(c._1 - 1, c._2 + 1, Direction.LEFT, 1) ++ recMoves(c._1 + 1, c._2 + 1, Direction.RIGHT, 1)
      } else {
        recMoves(c._1 - 1, c._2 - 1, Direction.LEFT, 1) ++ recMoves(c._1 + 1, c._2 - 1, Direction.RIGHT, 1)
      }
    } else {
      Array.empty[CoordStep]
    }
  }

  def outOfBoard(i: Int, j: Int): Boolean = i >= size || j >= size || i < 0 || j < 0

  def newPositionX(x: Integer, direction: Direction.Value): Integer = if (direction.equals(Direction.LEFT)) x - 1 else x + 1

  def newPositionY(y: Integer, colour: Colour.Value): Integer = if (colour.equals(Colour.BLACK)) y + 1 else y - 1

  @scala.annotation.tailrec
  final def recMoves(x: Int, y: Int, direction: Direction.Value, deep: Int): Array[CoordStep] = {
    if (deep > 2) return Array.empty[CoordStep]
    if (outOfBoard(x, y)) return Array.empty[CoordStep]
    // if field free and on board
    if (playfield.board(x)(y).isEmpty) if (deep == 2) return Array(new CoordStep((x, y), true)) else return Array(new CoordStep((x, y), false))
    // if same color at position, it is not possible to move the piece
    if (playfield.board(x)(y).isDefined && playfield.board(x)(y).get.colour.equals(currentPlayer)) return Array.empty[CoordStep]

    val newX = newPositionX(x, direction)
    val newY = newPositionY(y, currentPlayer)

    // recursiveMove
    recMoves(newX, newY, direction, deep + 1)
  }

  /**
    * Helper method to determine if a move is correct
    *
    * @param origin start position of piece
    * @param target end position of piece
    * @return true, if the move is correct
    */
  def isCorrectMove(origin: Coord, target: Coord): Boolean = playfield(target).isEmpty &&
    playfield(origin).exists(_.colour == currentPlayer) && getPossibleMoves.contains(new Move(origin, target))

  /**
    * handle a generic command
    *
    * @param command
    * @return
    */
  def handleCommand(command: Command): GameState = {
    command match {
      case QuitGame() => getState
      case SetPiece(start, end) =>
        movePiece(start, end); getState
      case GameStatus() => getState
    }
  }
}
