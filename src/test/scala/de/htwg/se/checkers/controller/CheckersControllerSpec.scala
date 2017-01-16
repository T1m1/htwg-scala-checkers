package de.htwg.se.checkers.controller

import java.util

import de.htwg.se.checkers.CheckersConfiguration
import de.htwg.se.checkers.model.Piece
import de.htwg.se.checkers.model.enumeration.Colour
import org.scalatest._

class CheckersControllerSpec extends WordSpec with Matchers {


  "A new Controller with CheckersConfiguration injection" should {
    val ctr = new CheckersController()(CheckersConfiguration)

    "be 3 playable rows" in {
      ctr.rows should be(3)
    }
    "be a size of 8" in {
      ctr.size should be(8)
    }

    ""
  }

}
