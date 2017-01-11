package de.htwg.se.checkers.view.gui

import akka.actor.{Actor, ActorRef}
import de.htwg.se.checkers.controller.{CreateUpdateUI, RegisterUI}

class Gui(controllerActor: ActorRef) extends Actor {
  controllerActor ! RegisterUI

  val frame = new SwingFrame(controllerActor)

  override def receive: Receive = {
    case infos: CreateUpdateUI  => frame.update(infos)
  }
}
