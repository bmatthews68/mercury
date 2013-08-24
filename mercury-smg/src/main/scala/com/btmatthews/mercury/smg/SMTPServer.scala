package com.btmatthews.mercury.smg

import akka.actor._

class SMTPServer(hostname: String, port: Integer) extends Actor with ActorLogging {

	override def preStart {
		IOManager(context.system).listen(hostname, port)
	}

	def receive = {
		case IO.Listening(server, address) =>
			log.info("Listening for SMTP traffic at " + address)
		case IO.NewClient(server) =>
			val connection = context.actorOf(Props[SMTPConnection])
			server.accept()(connection)
		case IO.Closed(server: IO.ServerHandle, cause) =>
			log.info("Stopped")
	}
}

object SMTPServer extends App {
	val port = Option(System.getenv("MERCURY_SMTP_PORT")) map (_.toInt) getOrElse 25
	val hostname = Option(System.getenv("MERCURY_SMTP_HOSTNAME")) getOrElse "localhost"
	ActorSystem().actorOf(Props(new SMTPServer(hostname, port)))
}
