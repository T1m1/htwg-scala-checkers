package de.htwg.se.checkers.model


case class Playfield(board: Vector[Vector[Option[Piece]]], size: Int) {

  // constructor for default parameter size
  def this(board: Vector[Vector[Option[Piece]]]) = this(board, board.size)

  // constructor to create empty field with size
  def this(size: Int) = this(Vector.tabulate(size, size) { (i, j) => None }, size)

  def setPiece(coord: Coord, piece: Piece): Playfield = {
    // assert(true) TODO check if the piece has correct x y values
    copy(board.updated(coord.x, board(coord.x).updated(coord.y, Some(piece))))
  }


  // only executed it is accessed the first time.
  lazy val numberOfRowsPerPlayer = calculateNumberOfRowsPerPlayer(board.length)

  def calculateNumberOfRowsPerPlayer(number: Int): Int = if (number == 3) 1 else (Math.ceil(number / 2) - 0.5).toInt


  //def set(x: Int, y: Int, playable: Boolean): PlayField = copy(board.updated(x * size + y, new Field(x, y, playable)))

}
