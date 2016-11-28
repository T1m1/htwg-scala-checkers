package de.htwg.se.checkers.model

import de.htwg.se.checkers.model.api.Coord

/**
  * Created by steffen on 21/11/2016.
  */



package object api {
  type Coord = (Int, Int)
}

object CoordUtil {

  implicit class BetterCoord(val c: Coord) {


    def oneStepRight(piece: Piece): Coord = (c._1 + 1, c._2 + 1)

    def oneStepLeft(piece: Piece): Coord = (c._1 - 1, c._2 + 1)
  }
}
