package de.htwg.se.checkers.view

import de.htwg.se.checkers.controller.CheckersController
import de.htwg.se.checkers.model.{Field, Piece, PlayField}

import scala.math.sqrt

class Tui(controller: CheckersController) {

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
      case _ =>
    }

    println
    continue
  }


  def myPrint(board: Vector[Vector[Field]]): Unit = {
    println(raw"\  1  2  3  4  5  6  7  8")
    val letter = ('A' to 'Z').toArray

    // Loop through two-dimensional array and print each piece.
    for (i <- board.indices) {
      print(s"${letter(i)} ")
      for (j <- board(i).indices) {
        print(board(i)(j).printBorder(" ") + " ")
      }
      println
    }
  }

}
