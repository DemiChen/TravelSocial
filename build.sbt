name := """TravelSocial"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-language:postfixOps")

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.1.1",
  "org.postgresql" % "postgresql" % "9.4-1202-jdbc41",
  "com.typesafe.play" %% "play-mailer" % "3.0.1",
  specs2 % Test,
	"org.specs2" %% "specs2-matcher-extra" % "3.6" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

scalariformSettings

fork in run := true
