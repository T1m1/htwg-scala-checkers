package de.htwg.se.checkers.view.gui


import java.awt._
import javax.swing.ImageIcon

import akka.actor.ActorRef
import de.htwg.se.checkers.controller.CheckersController
import de.htwg.se.checkers.model.enumeration.Colour

import scala.swing.event.ButtonClicked
import scala.swing.{Button, GridPanel, Label}

class GamePanel(controllerActor: ActorRef) extends GridPanel(0, 9) {

  val ALPHABET = ('A' to 'Z').toArray
  val light = Color.decode("#FCEBCC")
  val dark = Color.decode("#FF9B59")
  val possible = Color.decode("#FFFB1E")
  val selected = Color.decode("#3AFC3D")
  val screenSize = Toolkit.getDefaultToolkit.getScreenSize
  val dim = screenSize.height / 11
  var fields: Array[Array[Button]] = Array.ofDim[Button](8, 8)

  var ctrl: Option[CheckersController] = None
  var lastSelected: Array[(Int, Int, Color)] = Array()

  // add column descriptions
  contents += new Label
  for (column <- fields.indices) {
    contents += new Label {
      text = column.toString
      font = new Font(Font.SANS_SERIF, Font.BOLD, 40)
    }
  }

  // create and add buttons
  for {
    column <- fields.indices
    row <- fields.indices
  } {
    // create button
    val button = new Button() {
      margin = new Insets(0, 0, 0, 0)

      reactions += {
        case _: ButtonClicked => displayPossibleMoves(row, column)
      }
    }
    fields(row)(column) = button

    // add button
    if (row == 0) {
      contents += new Label {
        text = ALPHABET(column).toString
        font = new Font(Font.SANS_SERIF, Font.BOLD, 40)
      }
    }
    contents += button
  }


  def updateBoard(controller: CheckersController): Unit = {
    val board = controller.playfield.board
    val switchedBoard = for {
      i <- board.indices
    } yield for {
      j <- board.indices
    } yield board(j)(i)

    for {
      i <- switchedBoard.indices
      j <- switchedBoard(i).indices
    } {
      val button = fields(j)(i)
      if ((i + j) % 2 == 1) {
        button.background = dark
      } else {
        button.background = light
      }
      // set figures
      if (switchedBoard(i)(j).isDefined) {
        button.icon = new ImageIcon(scaleImageIcon(switchedBoard(i)(j).get.colour))
      } else {
        button.icon = null
      }
    }

    val possiblePieces = controller.getPossiblePieces
    for (
      i <- possiblePieces
    ) {
      fields(i._1)(i._2).background = possible
    }


  }

  def scaleImageIcon(figure: Colour.Value): Image = {
    val image = getClass.getResource("/image/" + figure + ".png")
    val scalesImage = new ImageIcon(image).getImage.getScaledInstance(dim, dim, java.awt.Image.SCALE_SMOOTH)
    scalesImage
  }

  def update(controller: CheckersController) {
    updateBoard(controller)
    ctrl = Some(controller)
  }

  def drawButton(move: ((Int, Int), Boolean)): Unit = {
    lastSelected :+=(move._1._1, move._1._2, fields(move._1._1)(move._1._2).background)
    fields(move._1._1)(move._1._2).background = selected
  }

  def displayPossibleMoves(row: Int, column: Int): Unit = {
    lastSelected.foreach(field => fields(field._1)(field._2).background = field._3)
    if (ctrl.isDefined && ctrl.get.playfield.board(row)(column).isDefined) {
      ctrl.get.getPossibleMoves((row, column)).foreach(move => drawButton(move))
    }
  }


}