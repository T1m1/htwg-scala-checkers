package de.htwg.se.checkers.view

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorRef}
import akka.pattern.ask
import de.htwg.se.checkers.controller.RegisterUI
import de.htwg.se.checkers.controller.command._
import de.htwg.se.checkers.model.api._
import de.htwg.se.checkers.model.enumeration.Colour
import de.htwg.se.checkers.model.{GameState, Piece, Playfield}

import scala.collection.immutable.IndexedSeq
import scala.concurrent.Await
import scala.io.StdIn
import scala.util.matching.Regex
import scala.util.matching.Regex.Match

case class Tui(controllerActor: ActorRef) extends Actor {

  implicit val timeout = akka.util.Timeout(5, TimeUnit.SECONDS)

  controllerActor ! RegisterUI

  override def receive: Receive = {
    case update: GameState =>
      printField(update.field)
      printGameOptions(update.currentPlayer)
      processInputLine(StdIn.readLine())
  }

  val ALPHABET: Array[Char] = ('A' to 'Z').toArray
  val Move: Regex = "([A-Z])([0-9])\\-([A-Z])([0-9])".r

  def printGameOptions(currentPlayer: Colour.Value): Unit = {
    println(
      s"""
         |\nPlayer: $currentPlayer it is your turn!
         |'p' -> print movable pieces
         |'m' -> print possible moves
         |'f' -> print playfield
         |'n' -> start new game
         |'q' -> quit game
         |move piece syntax: B2-A3
         |
         |your turn: """.stripMargin
    )
  }

  def processInputLine(input: String): Boolean = {
    var continue = true

    input match {
      case "q" => continue = false
      case "s" =>
        println("TODO start game")
      case "p" =>
        // print movable pieces
        val pieces = Await.result(controllerActor ? GetPossiblePieces, timeout.duration).asInstanceOf[IndexedSeq[(Int, Int)]]
        pieces foreach printPossiblePieces
        println("\nYour turn: ")
        controllerActor ! GameStatus
      case "n" => print("TODO start new game")
      case "m" =>
        // print possible moves
        val moves = Await.result(controllerActor ? GetMoves, timeout.duration).asInstanceOf[IndexedSeq[((Int, Int), (Int, Int))]]
        moves foreach printPossibleMoves
        controllerActor ! GameStatus
      case "f" => controllerActor ! GameStatus
      case Move(_*) => movePieceByInput(input)
      case _ => println("invalid input\n"); controllerActor ! GameStatus
    }

    println
    continue
  }

  def printField(playfield: Playfield): Unit = {
    val board = playfield.board
    print(board.indices.map(i => i + "\u205F").mkString("\\\u205F ", "", "\n"))

    // rotate board to display correct view
    val switchedBoard = for {
      i <- board.indices
    } yield for {
      j <- board.indices
    } yield board(j)(i)

    print(switchedBoard.zipWithIndex.map {
      case (row, index) => row.map(prettyPrint).mkString(ALPHABET(index) + " |", "|", "|")
    }.mkString("\n"))
  }

  def prettyPrint(pieces: Option[Piece]): String = pieces.fold("\u205F")(x => pieceToString(x))

  def pieceToString(piece: Piece): String = if (piece.colour == Colour.BLACK) "\u25CF" else "\u25CB"

  def printPossiblePieces(piece: (Int, Int)): Unit = print("(" + getCoordinate(piece) + ")")

  def printPossibleMoves(move: ((Int, Int), (Int, Int))): Unit = print(getCoordinate(move._1) + "-" + getCoordinate(move._2) + " ")

  def getCoordinate(coordinate: (Int, Int)): String = ALPHABET(coordinate._2) + "" + coordinate._1

  def movePieceByInput(input: String): Unit = Move.findAllIn(input).matchData foreach (m => parseGroupsAndMove(m))

  def parseGroupsAndMove(m: Match): Unit = {
    val origin = new Coord((m group 2).toInt, ALPHABET.indexOf(m.group(1).charAt(0)))
    val target = new Coord((m group 4).toInt, ALPHABET.indexOf(m.group(3).charAt(0)))

    // move piece if its a possible move
    //  if (controller.isCorrectMove(origin, target)) {
    //TODO: lets assume for a moment, that the move is correct
    controllerActor ! SetPiece(origin, target)
    //    } else {
    //      println("--- Move not possible! ---")
    //      controllerActor ! PrintInfo
    //    }

  }


}
