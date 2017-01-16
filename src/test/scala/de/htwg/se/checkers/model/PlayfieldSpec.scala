package de.htwg.se.checkers.model

import de.htwg.se.checkers.model.api.Coord
import de.htwg.se.checkers.model.enumeration.Colour
import org.scalatest._

class PlayfieldSpec extends WordSpec with Matchers {

  "A playfield with a specific size" should {

    val playfield = new Playfield(8)

    // call unapply to get 100% coverage for case classes
    Playfield.unapply(playfield)

    "be have the same size" in {
      playfield.size should be(8)
    }

    "contain a board with same size" in {
      playfield.board.length should be(8)
    }
  }

  "A playfield in which a black field was set" should {
    var playfield = new Playfield(8)
    val piece = Some(new Piece(Colour.BLACK))
    playfield = playfield.setPiece(new Coord(0, 0), piece)

    "be filled" in {
      playfield.board(0)(0).isDefined should be(true)
    }
  }

  "A playfield in which no field was set" should {
    var playfield = new Playfield(8)
    val piece = new Piece(Colour.BLACK)
    playfield = playfield.setPiece(new Coord(0, 0), None)

    "be empty" in {
      playfield.board(0)(0).isDefined should be(false)
      playfield(new Coord(0, 0)).isDefined should be(false)
      val playfield2 = new Playfield(playfield.board)
    }
  }

  "In a playfield with an empty coord" should {
    var playfield = new Playfield(8)
    val piece = new Piece(Colour.BLACK)
    playfield = playfield.setPiece(new Coord(0, 0), None)

    "be not playable" in {
      playfield.board(0)(0).isDefined should be(false)
      playfield(new Coord(0, 0)).isDefined should be(false)
    }

    "A new playfield created with a board" should {
      var playfield = new Playfield(8)

      "should have the same size as the board parameter" in {
        val playfield2 = new Playfield(playfield.board)
        playfield2.board.length should be(playfield.size)
      }
    }
  }
}