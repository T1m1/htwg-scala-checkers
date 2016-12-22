package de.htwg.se.checkers.view

import de.htwg.se.checkers.controller.CheckersController
import de.htwg.se.checkers.model.api._
import de.htwg.se.checkers.model.enumeration.Colour
import de.htwg.se.checkers.model.{Field, Piece, Playfield}

import scala.util.matching.Regex.Match

class Tui(controller: CheckersController) {

  val ALPHABET = ('A' to 'Z').toArray
  val Move = "\\[([A-Z])([0-9])\\-\\>([A-Z])([0-9])\\]".r


  def displayPossibleMoves(state: String) = {
    val currentPlayer = controller.currentPlayer

    state match {
      case "s" => {
        println(
          s"""
             |\nPlayer: $currentPlayer it is your turn!
             |'p' -> print movable pieces
             |'m' -> print possble moves
             |'n' -> start new game
             |'q' -> quit game
             |move pice syntax: [B2->A3]""".stripMargin)
      }
      case _ =>
    }
  }

  def processInputLine(input: String): Boolean = {
    var continue = true
    val currentPlayer = controller.currentPlayer

    input match {
      case "q" => continue = false
      case "s" =>
        println("start game")
        myPrint(controller.playfield.board)
        displayPossibleMoves("s")
      case "p" =>
        // print movable pieces
        controller.getPossiblePieces(controller.currentPlayer).foreach(piece => printPossiblePieces(piece))
        println("\nYour turn: ")
      case "n" => print("start new game")
      case "m" =>
        // print possible moves
        controller.getPossibleMoves(controller.currentPlayer).foreach(move => printPossibleMoves(move))
        println("\nYour turn: ")
      // print field
      case "f" => myPrint(controller.playfield.board)
      case Move(_*) =>
        movePieceByInput(input)
        myPrint(controller.playfield.board)
        displayPossibleMoves("s")
      case _ =>
    }

    println
    continue
  }

  def myPrint(board: Vector[Vector[Option[Piece]]]): Unit = {
    print(board.indices.map(i => i + "\u205F").mkString("\\\u205F ", "", "\n"))

    // rotate board to display correct view
    val switchedBoard = for {
      i <- board.indices
    } yield for {
      j <- board.indices
    } yield board(j)(i)

    print(switchedBoard.zipWithIndex.map {
      case (row, index) => row.map(prettyPrint).mkString(ALPHABET(index) + " |", "|", "|")
    }.mkString("\n"))
  }

  def prettyPrint(pieces: Option[Piece]): String = pieces.map(x => pieceToString(x)).getOrElse("\u205F")

  def pieceToString(piece: Piece): String = if (piece.colour == Colour.BLACK) "\u25CF" else "\u25CB"

  def printPossiblePieces(piece: (Int, Int)): Unit = print("(" + getCoordinate(piece) + ")")

  def printPossibleMoves(move: ((Int, Int), (Int, Int))): Unit = print("[" + getCoordinate(move._1) + "->" + getCoordinate(move._2) + "]")

  def getCoordinate(coordinate: (Int, Int)): String = ALPHABET(coordinate._2) + "" + coordinate._1

  def movePieceByInput(input: String) = Move.findAllIn(input).matchData foreach (m => parseGroupsAndMove(m))

  def parseGroupsAndMove(m: Match): Unit = controller.movePiece(new Coord((m group 2).toInt, ALPHABET.indexOf(m.group(1).charAt(0))), new Coord((m group 4).toInt, ALPHABET.indexOf(m.group(3).charAt(0))))
}
