package de.htwg.se.checkers

import de.htwg.se.checkers.controller.CheckersController
import de.htwg.se.checkers.model.PlayField
import de.htwg.se.checkers.view.Tui

object Checkers {

  // TODO inject size of playfield
  val playField = new PlayField(10)
  val ctr = new CheckersController(playField, 4)
  val tui = new Tui(ctr)

  def main(args: Array[String]): Unit = {
    println(s"Press 's' to start the game: ")
    while (tui.processInputLine(scala.io.StdIn.readLine())) {}
  }
}
