package de.htwg.se.checkers

import de.htwg.se.checkers.model.Playfield

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

  }

}
