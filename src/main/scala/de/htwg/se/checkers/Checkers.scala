package de.htwg.se.checkers

import de.htwg.se.checkers.controller.CheckersController
import de.htwg.se.checkers.model.Playfield
import de.htwg.se.checkers.view.Tui

object Checkers {

  implicit val bindingModule = CheckersConfiguration

  val ctr = new CheckersController()(bindingModule)
  val tui = new Tui(ctr)

  def main(args: Array[String]): Unit = {
    println(s"Press 's' to start the game: ")
    while (tui.processInputLine(scala.io.StdIn.readLine())) {}
  }
}
