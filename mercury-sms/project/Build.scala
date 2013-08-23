import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

	val appName = "mercury-sms"
	val appVersion = "1.0.0"

	val appDependencies = Nil

	val main = play.Project(
		appName, appVersion, appDependencies
	)
}
