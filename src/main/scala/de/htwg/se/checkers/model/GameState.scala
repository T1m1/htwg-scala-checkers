package de.htwg.se.checkers.model

import de.htwg.se.checkers.model.api.Coord
import de.htwg.se.checkers.model.enumeration.Colour

/**
 * Created by steffen on 16/01/2017.
 */
case class GameState(field: Playfield, currentPlayer: Colour.Value)

case class Targets(targets: Seq[Coord])

case class Origins(origins: Seq[Coord])

case class Moves(moves: Seq[(Coord, Coord)])
