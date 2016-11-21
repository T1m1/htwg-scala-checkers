package de.htwg.se.checkers.model

case class Field(x: Int, y: Int) {

  def isPlayable: Boolean = (x + y) % 2 == 0

  def printBorder(value: String): String = {
    if (isPlayable) {
      "[" + value + "]"
    } else {
      "|||"
    }
  }

}
