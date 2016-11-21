package de.htwg.se.checkers.model

import de.htwg.se.checkers.model.enumeration.Colour

case class Piece(x: Int, y: Int, colour: Colour.Value , checkers: Boolean) {

  def this(x: Int, y: Int, color: Colour.Value) = this(x, y, color, false)

}
