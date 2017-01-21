package de.htwg.se.checkers.view.gui

import java.awt._
import java.util.concurrent.TimeUnit
import javax.swing.ImageIcon

import akka.actor.ActorRef
import akka.pattern.ask
import de.htwg.se.checkers.controller.command._
import de.htwg.se.checkers.model.api.Coord
import de.htwg.se.checkers.model.enumeration.Colour
import de.htwg.se.checkers.model._

import scala.collection.immutable.IndexedSeq
import scala.concurrent.{Await, Future}
import scala.swing.event.ButtonClicked
import scala.swing.{Button, GridPanel, Label}
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

class GamePanel(controllerActor: ActorRef) extends GridPanel(0, 9) {

  implicit val timeout = akka.util.Timeout(5, TimeUnit.SECONDS)

  val ALPHABET = ('A' to 'Z').toArray
  val light = Color.decode("#FCEBCC")
  val dark = Color.decode("#FF9B59")
  val possible = Color.decode("#FFFB1E")
  val selected = Color.decode("#3AFC3D")
  val screenSize = Toolkit.getDefaultToolkit.getScreenSize
  val dim = screenSize.height / 14
  var fields: Array[Array[Button]] = Array.ofDim[Button](8, 8)

  var lastSelected: Array[(Int, Int, Color)] = Array()
  var selectedPiece: Option[(Int, Int)] = None

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

  def onFailure(e: Throwable): Unit = println("huhu")

  def drawButton(switchedBoard: IndexedSeq[IndexedSeq[Option[Piece]]], j: Int, i: Int, movablePieces: Array[(Int, Int)]): Unit = {
    val button = fields(j)(i)
    if (movablePieces.contains((j, i))) {
      button.background = possible
    } else {
      if ((i + j) % 2 == 1) {
        button.background = dark
      } else {
        button.background = light
      }
    }



    // set figures
    if (switchedBoard(i)(j).isDefined) {
      button.icon = new ImageIcon(scaleImageIcon(switchedBoard(i)(j).get.colour))
    } else {
      button.icon = null
    }
  }


  def updateBoard(state: GameState): Unit = {
    selectedPiece = None
    lastSelected = Array()

    var movablePiece: Array[Coord] = Array()

    val possiblePieces: Origins = Await.result(controllerActor ? GetPossiblePieces, timeout.duration).asInstanceOf[Origins]
    val possibleMoves: Moves = Await.result(controllerActor ? GetMoves, timeout.duration).asInstanceOf[Moves]

    for (elem <- possibleMoves.moves) {
      movablePiece :+= elem._1
    }

    val switchedBoard = state.field.board
    val board = for {
      i <- switchedBoard.indices
    } yield for {
      j <- switchedBoard.indices
    } yield switchedBoard(j)(i)

    for {
      i <- board.indices
      j <- board(i).indices
    } Future(drawButton(board, i, j, movablePiece))

  }

  def scaleImageIcon(figure: Colour.Value): Image = {
    val image = getClass.getResource("/image/" + figure + ".png")
    val scalesImage = new ImageIcon(image).getImage.getScaledInstance(dim, dim, java.awt.Image.SCALE_SMOOTH)
    scalesImage
  }

  def update(state: GameState): Unit = {
    updateBoard(state)
  }

  def drawButton(move: ((Int, Int))): Unit = {
    lastSelected :+=(move._1, move._2, fields(move._1)(move._2).background)
    fields(move._1)(move._2).background = selected
  }

  def displayPossibleMoves(row: Int, column: Int): Unit = {
    if (selectedPiece.isDefined) {
      val possibleMoves = Await.result(controllerActor ? GetMoves, timeout.duration).asInstanceOf[Moves]

      var a: Array[Coord] = Array[Coord]()
      for (elem <- possibleMoves.moves) {
        a :+= elem._2
      }
      if (a.contains((row, column))) {
        controllerActor ! SetPiece((selectedPiece.get._1, selectedPiece.get._2), (row, column))
      } else {
        selectedPiece = None
        controllerActor ! GameStatus
      }
    } else {
      lastSelected.foreach(field => fields(field._1)(field._2).background = field._3)
      val possiblePieces = Await.result(controllerActor ? GetPossiblePieces, timeout.duration).asInstanceOf[Origins]
      val possibleTargets = Await.result(controllerActor ? GetPossibleTargets((row, column)), timeout.duration).asInstanceOf[Targets]

      if (possiblePieces.origins.contains((row, column))) {
        drawButton((row, column))
        selectedPiece = Some((row, column))
        possibleTargets.targets.foreach(move => drawButton(move))
      }
    }
  }

}