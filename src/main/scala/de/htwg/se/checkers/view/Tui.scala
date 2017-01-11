package de.htwg.se.checkers.view

import akka.actor.{Actor, ActorRef}
import de.htwg.se.checkers.controller.command.{PrintInfo, SetPiece}
import de.htwg.se.checkers.controller.{CheckersController, CreateUpdateUI, RegisterUI}
import de.htwg.se.checkers.model.Piece
import de.htwg.se.checkers.model.api._
import de.htwg.se.checkers.model.enumeration.Colour

import scala.io.StdIn
import scala.util.matching.Regex.Match

class Tui(controllerActor: ActorRef) extends Actor {
  controllerActor ! RegisterUI

  val ALPHABET: Array[Char] = ('A' to 'Z').toArray
  val Move = "([A-Z])([0-9])\\-([A-Z])([0-9])".r


  def displayPossibleMoves(controller: CheckersController, state: String): Unit = {
    val currentPlayer = controller.currentPlayer

    state match {
      case "s" =>
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
             |your turn: """.stripMargin)
      case _ =>
    }
  }

  def processInputLine(controller: CheckersController, input: String): Boolean = {
    var continue = true
    val currentPlayer = controller.currentPlayer

    input match {
      case "q" => continue = false
      case "s" =>
        println("start game")
        controllerActor ! PrintInfo
      case "p" =>
        // print movable pieces
        controller.getPossiblePieces foreach printPossiblePieces
        println("\nYour turn: ")
        controllerActor ! PrintInfo
      case "n" => print("TODO start new game")
      case "m" =>
        // print possible moves
        controller.getPossibleMoves foreach printPossibleMoves
        controllerActor ! PrintInfo
      case "f" => controllerActor ! PrintInfo
      case Move(_*) => movePieceByInput(controller, input)
      case _ => println("invalid input\n"); controllerActor ! PrintInfo
    }

    println
    continue
  }

  def myPrint(board: Vector[Vector[Option[Piece]]]): Unit = {
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

  def movePieceByInput(controller: CheckersController, input: String): Unit = Move.findAllIn(input).matchData foreach (m => parseGroupsAndMove(controller, m))

  def parseGroupsAndMove(controller: CheckersController, m: Match): Unit = {
    val origin = new Coord((m group 2).toInt, ALPHABET.indexOf(m.group(1).charAt(0)))
    val target = new Coord((m group 4).toInt, ALPHABET.indexOf(m.group(3).charAt(0)))

    // move piece if its a possible move
    if (controller.isCorrectMove(origin, target)) {
      controllerActor ! SetPiece(origin, target)
    }
    else {
      println("--- Move not possible! ---")
      controllerActor ! PrintInfo
    }

  }

  override def receive: Receive = {
    case infos: CreateUpdateUI =>
      myPrint(infos.controller.playfield.board)
      displayPossibleMoves(infos.controller, "s")
      processInputLine(infos.controller, StdIn.readLine())
  }
}
