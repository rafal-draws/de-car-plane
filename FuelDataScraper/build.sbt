ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.4"

lazy val root = (project in file("."))
  .settings(
    name := "FuelDataScraper"
  )

libraryDependencies ++= Seq(
  "net.ruippeixotog" %% "scala-scraper" % "3.1.0",
  "com.typesafe.play" %% "play-json" % "2.9.4",
  "com.typesafe" % "config" % "1.3.3"
)