package de.htwg.se.checkers.view

import de.htwg.se.checkers.controller.CheckersController
import de.htwg.se.checkers.model.api._
import de.htwg.se.checkers.model.enumeration.Colour
import de.htwg.se.checkers.model.{Field, Piece, Playfield}

class Tui(controller: CheckersController) {

  val ALPHABET = ('A' to 'Z').toArray

  def displayPossibleMoves(state: String) = {
    val currentPlayer = controller.currentPlayer

    state match {
      case "s" => {
        println(
          s"""
             |\nPlayer: $currentPlayer it is your turn!
             |'p' -> print possble moves
             |'n' -> start new game
             |'q' -> quit game
             |move pice syntax: 2B->1A ??""".stripMargin)
      }
      case _ =>
    }
  }

  def processInputLine(input: String): Boolean = {
    var continue = true
    val currentPlayer = controller.currentPlayer

    input match {
      case "q" => continue = false
      case "s" => {
        println("start game")

        myPrint(controller.playfield.board)
        displayPossibleMoves("s")
      }
      // TODO print possible moves - debug version
      case "p" => {
        println("Possible pieces: " + controller.getPossiblePieces(Colour.WHITE).foreach(e => print(e)))
        println(controller.getPossiblePieces(Colour.BLACK).foreach(e => print(e)))
        println("Possible Moves: " +controller.getPossibleMoves(Colour.BLACK).foreach(e => e.foreach(a => print(a))))
        println(controller.getPossibleMoves(Colour.WHITE).foreach(e => e.foreach(a => print(a))))
      }
      case "n" => print("start new game")
      case "m" => print("move piece")
      // print field
      case "f" => myPrint(controller.playfield.board)
      case _ =>
    }

    println
    continue
  }


  def myPrint(board: Vector[Vector[Option[Piece]]]): Unit = {
    print(board.indices.map(i => (i + 1) + "\u205F").mkString("\\\u205F ", "", "\n"))
    print(board.zipWithIndex.map {
      case (row, index) => row.map(prettyPrint).mkString(ALPHABET(index) + " |", "|", "|")
    }.mkString("\n"))
  }

  def prettyPrint(pieces: Option[Piece]): String = pieces.map(x => pieceToString(x)).getOrElse("\u205F")

  def pieceToString(piece: Piece): String = if (piece.colour == Colour.BLACK) "\u25CF" else "\u25CB"

}
