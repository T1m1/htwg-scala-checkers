package de.htwg.se.checkers.model

case class PlayField(size: Int) {
  val field = Array.ofDim[Any](size, size)

  init()

  def init(): Unit = {
    val numberOfRowsPerPlayer = getNumberOfRowsPerPlayer(size)
    // Feld Zusammenbauen
  }

  // TODO
  private def getNumberOfRowsPerPlayer(number: Int): Int = {
    number / 2 - 0.5.toInt
  }


}
