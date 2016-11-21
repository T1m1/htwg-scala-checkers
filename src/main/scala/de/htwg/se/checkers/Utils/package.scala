package de.htwg.se.checkers

import de.htwg.se.checkers.model.Playfield

/**
  * Created by steffen on 21/11/2016.
  */
object Utils {

  implicit class betterInt(i: Int) {

    def isEven: Boolean = i % 2 == 0

    def isOdd: Boolean = i % 2 == 1
  }


}

object CheckerRules {

  implicit class rule(field: Playfield) {

    def ensureCorrectRows(rows: Int): Boolean = rows > 0 && field.size / 2 > rows

  }

}