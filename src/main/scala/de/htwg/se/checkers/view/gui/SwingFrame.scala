package de.htwg.se.checkers.view.gui

import java.awt.{ Dimension, Toolkit }

import akka.actor.ActorRef
import de.htwg.se.checkers.controller.CreateUpdateUI

import scala.swing._
import scala.swing.event.Key

class SwingFrame(controllerActor: ActorRef) extends Frame {
  title = "Checkers"
  menuBar = buildMenuBar

  val gamePanel = new GamePanel(controllerActor)

  contents = new BorderPanel {
    layout(gamePanel) = BorderPanel.Position.Center
  }

  peer.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)

  val dim = Toolkit.getDefaultToolkit.getScreenSize.height - 100
  size = new Dimension(dim, dim)
  visible = true

  def update(info: CreateUpdateUI): Unit = {
    gamePanel.update(info.controller)
  }

  def buildMenuBar: MenuBar = {
    new MenuBar {
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
  }

}
