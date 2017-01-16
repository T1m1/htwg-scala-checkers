package de.htwg.se.checkers.controller.command

import de.htwg.se.checkers.model.api._

trait Command {}

case class SetPiece(start: Coord, end: Coord) extends Command

case class QuitGame() extends Command

case class NewGame() extends Command

case class PrintInfo() extends Command

case class GetMoves() extends Command

case class GetCurrentPlayer() extends Command

case class GetPossiblePieces() extends Command