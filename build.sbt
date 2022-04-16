name := "ds-store-remover"

organization := "org.bruchez.olivier"

version := "1.0"

scalaVersion := "2.13.8"

libraryDependencies ++= Seq("commons-io" % "commons-io" % "2.6")

initialCommands := "import org.bruchez.olivier.dsstoreremover._"

scalafmtOnCompile in ThisBuild := true