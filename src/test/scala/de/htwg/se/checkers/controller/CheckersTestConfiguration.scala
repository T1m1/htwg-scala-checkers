package de.htwg.se.checkers.controller

import com.escalatesoft.subcut.inject._
import de.htwg.se.checkers.model.enumeration.Colour

object BindingKeys {

  object PlayfieldSize extends BindingId

  object NumberOfPlayableRows extends BindingId

  object ColourPlayerOne extends BindingId

  object ColourPlayerTwo extends BindingId

}

object CheckersTestConfiguration extends NewBindingModule(module => {
  import BindingKeys._
  import module._

  bind[Int] idBy PlayfieldSize toSingle 8
  bind[Int] idBy NumberOfPlayableRows toSingle 3
  bind[Colour.Value] idBy ColourPlayerOne toSingle Colour.BLACK
  bind[Colour.Value] idBy ColourPlayerTwo toSingle Colour.WHITE

})


