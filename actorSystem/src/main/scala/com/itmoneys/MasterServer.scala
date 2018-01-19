package com.itmoneys

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorSystem, Props}
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

class MasterServer extends Actor{
	
	def receive(): Receive = {
		case (msg: String) =>{
			msg match {
				case "a" =>{
					println(11)
					implicit val timeout = Timeout(3, TimeUnit.SECONDS)
					//akka.tcp://worker@127.0.0.1:19092
					context.actorSelection("akka.tcp://worker@127.0.0.1:19092/user/worker") ! "a"
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

object MasterServer{
	def main(args: Array[String]): Unit = {
		val conf = ConfigFactory.load("master.conf")
		val actorSystem: ActorSystem = ActorSystem("master", conf)
//		val actorSystem: ActorSystem = ActorSystem("RemoteDemoSystem")
		val server = actorSystem.actorOf(Props[MasterServer], "server")
//		val server = actorSystem.actorOf(Props[MasterServer], "master")
		server ! "a"
		
		
	}
}
