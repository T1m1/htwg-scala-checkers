package de.htwg.se.checkers

import akka.actor.{ActorSystem, Props}
import de.htwg.se.checkers.controller.{CheckersController, ControllerActor}
import de.htwg.se.checkers.view.Tui

object Checkers {

  // inject
  implicit val bindingModule = CheckersConfiguration

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem.create("checkers")
    val controllerActor = actorSystem.actorOf(Props(new ControllerActor()))

    // tui
    actorSystem.actorOf(Props(new Tui(controllerActor)))
  }
}
