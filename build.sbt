name := """bankingapp"""
organization := "tala.co"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

lazy val postgresversion = "9.4-1201-jdbc41"
libraryDependencies ++= Seq(
  jdbc,
  guice,
  evolutions,
  javaJpa,
  ws,
  caffeine,
  "org.hibernate" % "hibernate-core" % "5.4.9.Final",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "org.scalactic" %% "scalactic" % "3.2.2",
  "com.typesafe.slick" %% "slick" % "3.3.2",
  "com.typesafe.slick" %% "slick-testkit" % "3.3.3" % "test",
  "com.typesafe.play" %% "play-json" % "2.8.0-M5",


  "com.typesafe.slick" %% "slick" % "3.2.1",
  "com.typesafe.play" %% "play-slick" % "4.0.2",

  "org.scalactic" %% "scalactic" % "3.2.0",
  "com.typesafe.slick" %% "slick-codegen" % "3.3.2",
  "com.github.tminglei" %% "slick-pg" % "0.19.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",

  "org.scalatest" %% "scalatest-flatspec" % "3.2.0" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % "test",
  "org.scalatestplus" %% "mockito-3-4" % "3.2.2.0" % "test"

)

