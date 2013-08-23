package com.btmatthews.mercury.smg

import akka.actor._

class SMTPServer(hostname: String, port: Integer) extends Actor with ActorLogging {

	override def preStart {
		IOManager(context.system).listen(hostname, port)
	}

	def receive = {
		case IO.NewClient(server) =>
			server.accept()
		case IO.Read(rHandle, bytes) =>
			rHandle.asSocket write bytes.compact
	}
}

object SMTPServer extends App {
	val port = Option(System.getenv("MERCURY_SMTP_PORT")) map (_.toInt) getOrElse 25
	val hostname = Option(System.getenv("MERCURY_SMTP_HOSTNAME")) getOrElse "localhost"
	ActorSystem().actorOf(Props(new SMTPServer(hostname, port)))
}
