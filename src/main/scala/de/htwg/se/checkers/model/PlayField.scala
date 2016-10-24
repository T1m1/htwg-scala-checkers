package de.htwg.se.checkers.model

case class PlayField(size: Int) {
  val field = Array.ofDim[Any](size, size)
  val numberOfRowsPerPlayer = calculateNumberOfRowsPerPlayer(size)


  def calculateNumberOfRowsPerPlayer(number: Int): Int = {
    if (number == 3) {
      1
    }
    (Math.ceil(number / 2) - 0.5).toInt
  }


}
