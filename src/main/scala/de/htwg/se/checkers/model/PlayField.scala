package de.htwg.se.checkers.model

import de.htwg.se.checkers.model.api.Coord


case class Playfield(board: Vector[Vector[Option[Piece]]], size: Int) {


  def apply(pos: Coord): Option[Piece] = board(pos._1)(pos._2)


  // constructor for default parameter size
  def this(board: Vector[Vector[Option[Piece]]]) = this(board, board.size)

  // constructor to create empty field with size
  def this(size: Int) = this(Vector.tabulate(size, size) { (i, j) => None }, size)

  def setPiece(coord: Coord, piece: Option[Piece]): Playfield = {
    // assert(true) TODO check if the piecqe has correct x y values
    copy(board.updated(coord._1, board(coord._1).updated(coord._2, piece)))
  }

}
