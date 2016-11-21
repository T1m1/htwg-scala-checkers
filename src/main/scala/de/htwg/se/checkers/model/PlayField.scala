package de.htwg.se.checkers.model

case class PlayField(board: Vector[Vector[Field]], size: Int) {

  val ALPHABET = ('A' to 'Z').toArray

  // constructor for default parameter size
  def this(board: Vector[Vector[Field]]) = this(board, board.size)

  // constructor to create field with size
  def this(size: Int) = this(Vector.tabulate(size, size) { (i, j) => new Field(i, j) }, size)

  // only executed it is accessed the first time.
  lazy val numberOfRowsPerPlayer = calculateNumberOfRowsPerPlayer(board.length)

  def calculateNumberOfRowsPerPlayer(number: Int): Int = if (number == 3) 1 else (Math.ceil(number / 2) - 0.5).toInt

  override def toString: String = board.zipWithIndex.map { case (row, j) => row.mkString(ALPHABET(j) + " [", ",", "]") }.mkString("\n")


  //def set(x: Int, y: Int, playable: Boolean): PlayField = copy(board.updated(x * size + y, new Field(x, y, playable)))
}
