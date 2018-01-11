package com.itmoneys

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

class WorkerServer extends Actor{
	
	def receive(): Receive = {
		case "a" =>{
			println(1)
		}
		case "b" =>{
			println(2)
		}
		case _ =>{
			println(3)
		}
	}
	
}

object WorkerServer {
	def main(args: Array[String]): Unit = {
//		val workerConf = ConfigFactory.load("worker.conf")
//		val workerContext: ActorSystem = ActorSystem("master", workerConf)
//		val worker = workerContext.actorOf(Props[WorkerServer].withDispatcher("akka.dispatcher.server"), "worker")
	}
}
