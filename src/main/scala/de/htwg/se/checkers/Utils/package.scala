package de.htwg.se.checkers

import de.htwg.se.checkers.model.api.Coord
import de.htwg.se.checkers.model.{Coord, Playfield}

/**
  * Created by steffen on 21/11/2016.
  */
object Utils {

  implicit class BetterInt(i: Int) {

    def isEven: Boolean = i % 2 == 0

    def isOdd: Boolean = i % 2 == 1
  }


}

object CheckerRules {

  implicit class Rule(field: Playfield) {

    // There must be at least one empty row on the field at the beginning of the game
    def ensureCorrectRows(rows: Int): Boolean = rows > 0 && field.size / 2 > rows

    // If you can take a piece, you have to. Each possible move is stored in this set
    def possibleMoves(): Set[Coord] = Set(new Coord(1, 1))

    def listTargets(origin: Coord): Set[Coord] = field(origin).getOrElse() match {
      case None => Set.empty

    }

  }

}
