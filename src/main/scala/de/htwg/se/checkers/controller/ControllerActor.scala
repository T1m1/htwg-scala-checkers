package de.htwg.se.checkers.controller

import akka.actor.{Actor, ActorRef}
import de.htwg.se.checkers.CheckersConfiguration
import de.htwg.se.checkers.controller.command._
import de.htwg.se.checkers.model._

import scala.collection.mutable.ListBuffer

case object RegisterUI

case object DeregisterUI

class ControllerActor() extends Actor {
  implicit val bindingModule = CheckersConfiguration

  val controller: CheckersController = new CheckersController()(bindingModule)
  val userInterfaces = new ListBuffer[ActorRef]()

  override def receive: Receive = {

    // Handle register/deregister of Listeners
    case RegisterUI =>
      userInterfaces += sender(); sender() ! controller.getState
    case DeregisterUI => userInterfaces -= sender()

    // Ask - Pattern
    case GetMoves => sender ! Moves(controller.getPossibleMoves)
    case GetPossiblePieces => sender ! Origins(controller.getPossiblePieces)
    case GetPossibleTargets(coord) => sender ! Targets(controller.getPossibleTargets(coord))
    case GetCurrentPlayer => sender ! controller.currentPlayer
    case GameStatus => sender ! controller.getState

    // Handle command and notify all other Listeners
    case QuitGame => userInterfaces.foreach(_ ! Exit())

    case command: Command =>
      val state = controller.handleCommand(command)
      // update listener
      userInterfaces.foreach(_ ! state)

    case _ => print("Unrecognised Command in Controller Actor")
  }
}
