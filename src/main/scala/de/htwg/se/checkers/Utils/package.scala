package de.htwg.se.checkers

/**
  * Created by steffen on 21/11/2016.
  */
object Utils {

  implicit class betterInt(i: Int) {

    def isEven: Boolean = i % 2 == 0
  }

}
