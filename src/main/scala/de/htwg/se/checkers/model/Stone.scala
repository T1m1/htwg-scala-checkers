package de.htwg.se.checkers.model

case class Stone(x: Int, y: Int, checkers: Boolean) {

  def this(x: Int, y: Int) {
    this(x, y, false)
  }
}
