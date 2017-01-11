package de.htwg.se.checkers.view.gui

import java.awt.{Dimension, Toolkit}

import akka.actor.ActorRef
import de.htwg.se.checkers.controller.CreateUpdateUI
import de.htwg.se.checkers.model.Playfield

import scala.swing._
import scala.swing.event.Key

class SwingFrame(controllerActor: ActorRef) extends Frame {

  menuBar = new MenuBar {
    contents += new Menu("Game") {
      mnemonic = Key.G
      contents += new MenuItem(Action("New") {
        controllerActor ! println("restart game")
      })
      contents += new MenuItem(Action("Quit") {
        controllerActor ! println("quit game")
      })
    }
  }

  val gamePanel = new GamePanel(controllerActor)

  contents = new BorderPanel {
    layout(gamePanel) = BorderPanel.Position.Center
  }

  title = "Chess"
  peer.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)

  val screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  size = new Dimension(screenSize.width, screenSize.height)
  visible = true

  def update(info: CreateUpdateUI): Unit = {
    gamePanel.update(info.controller.playfield)
  }

}
