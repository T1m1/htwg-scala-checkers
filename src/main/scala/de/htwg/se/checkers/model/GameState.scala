package de.htwg.se.checkers.model

import de.htwg.se.checkers.model.enumeration.Colour

/**
 * Created by steffen on 16/01/2017.
 */
case class GameState(field: Playfield, currentPlayer: Colour.Value)
