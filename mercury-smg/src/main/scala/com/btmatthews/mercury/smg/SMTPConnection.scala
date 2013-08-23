package com.btmatthews.mercury.smg

import akka.actor._

class SMTPConnection extends Actor with ActorLogging {
	def receive = {
		case _ => log.info("received unknown message")
	}
}
