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
        println(s"\n\nPlayer: $currentPlayer it is your turn!")
        println(s"'p' -> print possble moves")
        println(s"'n' -> start new game")
        println(s"'q' -> quit game")
        println(s"move pice syntax: 2B->1A ??")
        println
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
      case "p" => println(controller.getPossibleMoves(Colour.WHITE))
      case "n" => print("start new game")
      case "m" => print("move piece")
      case "f" => print("display Playfield")
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
