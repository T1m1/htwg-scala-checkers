package de.htwg.se.checkers.model


case class PlayField(board: Vector[Vector[Option[Piece]]], size: Int) {

  def setPiece(coord: Coord, piece: Piece): PlayField = {
    // assert(true) TODO check if the piece has correct x y values
    copy(board.updated(coord.x, board(coord.x).updated(coord.y, Some(piece))))
  }

  def movePiece(origin: Coord, destiny: Coord): PlayField = ???

  // constructor for default parameter size
  def this(board: Vector[Vector[Option[Piece]]]) = this(board, board.size)

  // constructor to create field with size
  def this(size: Int) = this(Vector.tabulate(size, size) { (i, j) => None }, size)

  // only executed it is accessed the first time.
  lazy val numberOfRowsPerPlayer = calculateNumberOfRowsPerPlayer(board.length)

  def calculateNumberOfRowsPerPlayer(number: Int): Int = if (number == 3) 1 else (Math.ceil(number / 2) - 0.5).toInt


  //def set(x: Int, y: Int, playable: Boolean): PlayField = copy(board.updated(x * size + y, new Field(x, y, playable)))
}
