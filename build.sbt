name := """TravelSocial"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-language:postfixOps")

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.1.1",
  "mysql" % "mysql-connector-java" % "5.1.37",
  "com.etaty.rediscala" %% "rediscala" % "1.5.0",
  "com.github.t3hnar" %% "scala-bcrypt" % "2.5",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.1.0",
  specs2 % Test,
	"org.specs2" %% "specs2-matcher-extra" % "3.6" % Test
)

resolvers ++= Seq(
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  "rediscala" at "http://dl.bintray.com/etaty/maven"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

scalariformSettings

fork in run := true
