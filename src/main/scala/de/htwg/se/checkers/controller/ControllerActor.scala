package de.htwg.se.checkers.controller

import akka.actor.{Actor, ActorRef}
import de.htwg.se.checkers.CheckersConfiguration
import de.htwg.se.checkers.controller.command.{Command, PrintInfo}

import scala.collection.mutable.ListBuffer

case object RegisterUI

case object DeregisterUI

class ControllerActor() extends Actor {
  implicit val bindingModule = CheckersConfiguration

  val controller: CheckersController = new CheckersController()(bindingModule)
  val userInterfaces = new ListBuffer[ActorRef]()

  override def receive: Receive = {
    case RegisterUI => userInterfaces += sender(); sender() ! createUI()
    case DeregisterUI => userInterfaces -= sender()
    case PrintInfo => userInterfaces.foreach(_ ! updateUI())
    case command: Command => controller.handleCommand(command) match {
      case true => userInterfaces.foreach(_ ! updateUI())
    }
  }

  private def updateUI() = {
    UpdateUI(controller)
  }

  private def createUI() = {
    CreateUI(controller)
  }
}

case class UpdateUI(controller: CheckersController)

case class CreateUI(controller: CheckersController)
