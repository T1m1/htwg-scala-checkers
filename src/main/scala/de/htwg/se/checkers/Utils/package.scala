package de.htwg.se.checkers

import de.htwg.se.checkers.model.PlayField

/**
  * Created by steffen on 21/11/2016.
  */
object Utils {

  implicit class betterInt(i: Int) {

    def isEven: Boolean = i % 2 == 0
  }

}

object CheckerRules {

  implicit class rule(field: PlayField) {

    //def correctPiece: Boolean = field.

  }
}