package de.htwg.se.checkers.view

import de.htwg.se.checkers.controller.CheckersController
import de.htwg.se.checkers.model.enumeration.Colour
import de.htwg.se.checkers.model.{Field, Piece, PlayField}

class Tui(controller: CheckersController) {

  val ALPHABET = ('A' to 'Z').toArray


  def processInputLine(input: String): Boolean = {
    var continue = true
    val currentPlayer = controller.currentPlayer

    input match {
      case "q" => continue = false
      case "s" => {
        println("start game")

        println(s"Player: $currentPlayer it is your turn!")
        myPrint(controller.playfield.board)
      }
      case "p" => print("print possible moves")
      case "n" => print("start new game")
      case "m" => print("move piece")
      case "a" => print("display all possible moves")
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

  def prettyPrint(field: Option[Piece]): String = field.map(x => pieceToString(x)).getOrElse("\u205F")

  def pieceToString(piece: Piece): String = if (piece.colour == Colour.BLACK) "\u25CF" else "\u25CB"

}
