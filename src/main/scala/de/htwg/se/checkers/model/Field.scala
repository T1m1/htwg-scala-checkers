package de.htwg.se.checkers.model
import  de.htwg.se.checkers.Utils._

case class Field(x: Int, y: Int, piece: Option[Piece]) {

  def this(x: Int, y: Int) = this(x, y, None)

  def isPlayable: Boolean = (x + y).isEven
  
}
