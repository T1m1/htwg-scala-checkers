package de.htwg.se.checkers.controller

import de.htwg.se.checkers.model.PlayField

class CheckersController(val playfield: PlayField) {

  val currentPlayer = 0

  // TODO
  def initPlayfiled() : PlayField = {
    // set pieces for all player and return a copy of playfield
    playfield
  }
}
