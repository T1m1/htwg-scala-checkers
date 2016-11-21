package de.htwg.se.checkers.model

case class Field(x: Int, y: Int, playable: Boolean) {

  def printBorder(value: String): String = {
    if (playable) {
      "[" + value + "]"
    } else {
      "|||"
    }
  }

}
