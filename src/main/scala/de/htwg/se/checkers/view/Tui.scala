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
        myPrint(controller.playfield)
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


  def myPrint(board: PlayField): Unit = {
    println(raw"\  1  2  3  4  5  6  7  8")

    print(board)

  }

}
