package com.itmoneys

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

class MasterServer extends Actor{
	
	def receive(): Receive = {
		case (msg: String) =>{
			msg match {
				case "a" =>{
					println(11)
				}
				case "b" =>{
					println(22)
				}
				case _ =>{
					println(33)
				}
			}
		}
		case (msg: Int) =>{
			msg match {
				case 0 =>{
					println(1)
				}
				case 1 =>{
					println(2)
				}
				case _ =>{
					println(3)
				}
			}
		}

	}
	
}

object MasterServer {
	def main(args: Array[String]): Unit = {
//		val conf = ConfigFactory.load("master.conf")
//		val context: ActorSystem = ActorSystem("master", conf)
		val context: ActorSystem = ActorSystem("RemoteDemoSystem")
//		val server = context.actorOf(Props[MasterServer].withDispatcher("akka.dispatcher.server"), "server")
		val server = context.actorOf(Props[MasterServer], "master")
	}
}
