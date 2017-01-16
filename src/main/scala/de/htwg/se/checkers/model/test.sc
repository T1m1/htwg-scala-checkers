import de.htwg.se.checkers.model.enumeration.Colour
import de.htwg.se.checkers.model.{Field, Piece, Coord}

object Test {

  //  val p = Some(new Piece(Colour.BLACK))
  //  val n = None
  //
  //  print(n.getOrElse())
  //  val ALPHABET = ('A' to 'Z').toArray
  //
  //  val a = 2
  //
  val size = 8
  val board = Vector.tabulate(size, size) { (i, j) => if (i == 1 && j == 1) Some(new Piece(Colour.WHITE)) else Some(new Piece(Colour.BLACK)) }


  var set: Vector[Int] = Vector()
  set ++= Range(0, 8).filter(i => board(1)(i).exists(_.colour == Colour.WHITE))

  print(set)


  //
  //  print(board.zipWithIndex.map {
  //    case (row, index) => row.map(prettyPrint).mkString(ALPHABET(index) + " |", "", "| ")
  //  }.mkString("\n"))
  //
  //
  //  def prettyPrint(field: Field): String = field.piece.map(x => "|" + pieceToString(x) + "|").getOrElse("| |")
  //
  //  def pieceToString(piece: Piece): String = if (piece.colour == Colour.BLACK) "\u25CB" else "\u25CF"
  //


}