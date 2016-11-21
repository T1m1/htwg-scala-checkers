package de.htwg.se.checkers.model

case class Piece(x: Int, y: Int, color: Int, checkers: Boolean) {

  def this(x: Int, y: Int, color: Int) = this(x, y, color, false)

}
