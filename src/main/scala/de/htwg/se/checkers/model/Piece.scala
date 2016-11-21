package de.htwg.se.checkers.model

import de.htwg.se.checkers.model.enumeration.Colour

case class Piece(colour: Colour.Value , checkers: Boolean) {

  def this(color: Colour.Value) = this(color, false)

}
