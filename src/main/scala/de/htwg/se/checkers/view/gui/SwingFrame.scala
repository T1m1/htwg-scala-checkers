package de.htwg.se.checkers.view.gui

import java.awt.{Dimension, Toolkit}

import akka.actor.ActorRef
import de.htwg.se.checkers.CheckersConfiguration
import de.htwg.se.checkers.controller.command.QuitGame
import de.htwg.se.checkers.model.GameState

import scala.swing._
import scala.swing.event.Key

class SwingFrame(controllerActor: ActorRef) extends Frame {
  def exit: Unit = dispose()

  title = "Checkers"
  menuBar = buildMenuBar

  implicit val bindingModule = CheckersConfiguration
  val gamePanel = new InjectablePanel(controllerActor)(bindingModule).gamePanel

  contents = new BorderPanel {
    layout(gamePanel) = BorderPanel.Position.Center
  }

  peer.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)

  val dim = Toolkit.getDefaultToolkit.getScreenSize.height - 300
  size = new Dimension(dim, dim)
  visible = true

  def update(state: GameState): Unit = {
    gamePanel.update(state)
  }

  def buildMenuBar: MenuBar = {
    new MenuBar {
      contents += new Menu("Game") {
        mnemonic = Key.G
        contents += new MenuItem(Action("New") {
          controllerActor ! println("restart game")
        })
        contents += new MenuItem(Action("Quit") {
          controllerActor ! QuitGame
        })
      }
    }
  }

}
