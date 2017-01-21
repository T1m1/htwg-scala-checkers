package de.htwg.se.checkers.model

import de.htwg.se.checkers.Utils.Utils._

case class Field(x: Int, y: Int, piece: Option[Piece]) {
  //assert(!isPlayable && piece.isDefined)

  def this(x: Int, y: Int) = this(x, y, None)

  def isPlayable: Boolean = (x + y).isEven

}
