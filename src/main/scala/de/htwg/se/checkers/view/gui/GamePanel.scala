package de.htwg.se.checkers.view.gui


import java.awt.{Color, Dimension, Insets, Toolkit}
import javax.swing.ImageIcon

import akka.actor.ActorRef
import de.htwg.se.checkers.model.{Piece, Playfield}

import scala.swing.{Button, GridPanel, Label}


class GamePanel(controllerActor: ActorRef) extends GridPanel(0, 9) {

  private val cols = Array("A", "B", "C", "D", "E", "F", "G", "H")
  private val light = Color.decode("#FCEBCC")
  private val dark = Color.decode("#FF9B59")

  val screenSize = Toolkit.getDefaultToolkit.getScreenSize;
  val dim = screenSize.height / 11

  var chessButtons: Array[Array[Button]] = Array.ofDim[Button](8, 8)

  // add column descriptions
  contents += new Label
  for (column <- chessButtons.indices) {
    contents += new Label {
      text = cols(column)
    }
  }

  // create and add buttons
  for {
    column <- chessButtons.indices
    row <- chessButtons.indices
  } {
    // create button
    val button = new Button() {
      margin = new Insets(0, 0, 0, 0)
      /*
        reactions += {
          case _: ButtonClicked => controller ! MoveCmd(row, column)
        }
*/
    }
    chessButtons(row)(column) = button

    // add button
    if (row == 0) {
      contents += new Label {
        text = (column + 1).toString
      }
    }
    contents += button
  }


  def updateBoard(board: Vector[Vector[Option[Piece]]]): Unit = {
    val switchedBoard = for {
      i <- board.indices
    } yield for {
      j <- board.indices
    } yield board(j)(i)

    for {
      i <- switchedBoard.indices
      j <- switchedBoard(i).indices
    } {
      val button = chessButtons(i)(j)
      if ((i + j) % 2 == 1) {
        button.background = dark
      } else {
        button.background = light
      }
      // set figures
      if (switchedBoard(i)(j).isDefined) {
        val figure = switchedBoard(i)(j).get.colour
        val image = getClass.getResource("/image/" + figure + ".png")
        val scalesImage = new ImageIcon(image).getImage.getScaledInstance(dim, dim, java.awt.Image.SCALE_SMOOTH)
        button.icon = new ImageIcon(scalesImage)

        //button.icon = new ImageIcon(aaa)
      } else {
        button.icon = null
      }
    }

  }

  def update(playfield: Playfield) {
    updateBoard(playfield.board)
  }
}