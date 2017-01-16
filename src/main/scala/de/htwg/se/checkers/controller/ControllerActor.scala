package de.htwg.se.checkers.controller

import akka.actor.{ Actor, ActorRef }
import de.htwg.se.checkers.CheckersConfiguration
import de.htwg.se.checkers.controller.command.{ Command, PrintInfo }

import scala.collection.mutable.ListBuffer

case object RegisterUI

case object DeregisterUI

class ControllerActor() extends Actor {
  implicit val bindingModule = CheckersConfiguration

  val controller: CheckersController = new CheckersController()(bindingModule)
  val userInterfaces = new ListBuffer[ActorRef]()

  override def receive: Receive = {
    case RegisterUI =>
      userInterfaces += sender(); sender() ! createUpdateUI()
    case DeregisterUI => userInterfaces -= sender()
    case PrintInfo => userInterfaces.foreach(_ ! createUpdateUI())
    case command: Command => controller.handleCommand(command) match {
      case true => userInterfaces.foreach(_ ! createUpdateUI())
    }
  }

  private def createUpdateUI() = {
    CreateUpdateUI(controller)
  }
}

case class CreateUpdateUI(controller: CheckersController)