package de.htwg.se.checkers.model

import de.htwg.se.checkers.model.api.Coord
import de.htwg.se.checkers.model.enumeration.Direction


package object api {
  type Coord = (Int, Int)
}

object CoordUtil {

  implicit class BetterCoord(val c: Coord) {

    def oneStepRight(piece: Piece, direction: Direction.Value): Coord = {
      if (direction.equals(Direction.DOWN)) (c._1 + 1, c._2 + 1) else (c._1 + 1, c._2 - 1)
    }

    def oneStepLeft(piece: Piece, direction: Direction.Value): Coord = {
      if (direction.equals(Direction.DOWN)) (c._1 - 1, c._2 + 1) else (c._1 - 1, c._2 - 1)
    }
  }

}
