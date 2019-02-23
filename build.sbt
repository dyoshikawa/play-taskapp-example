name := "play27"

version := "1.0"

lazy val `play27` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(jdbc,
  ehcache,
  ws,
  specs2 % Test,
  guice,
  "org.postgresql" % "postgresql" % "42.2.5",
  "org.scalikejdbc" %% "scalikejdbc" % "3.3.2",
  "org.scalikejdbc" %% "scalikejdbc-config" % "3.3.2",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.7.0-scalikejdbc-3.3",
  "io.circe" %% "circe-core" % "0.11.0",
  "io.circe" %% "circe-generic" % "0.11.0",
  "io.circe" %% "circe-parser" % "0.11.0",
  "com.dripower" %% "play-circe" % "2711.0"
)

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

      