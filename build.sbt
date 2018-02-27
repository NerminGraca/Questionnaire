name := """questionnaire"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.webjars" % "bootstrap" % "3.3.7",
  "org.webjars.bower" % "jquery" % "2.1.4",
  "org.postgresql" % "postgresql" % "9.4.1208",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.apache.commons" % "commons-email" % "1.5"
)


// Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
EclipseKeys.preTasks := Seq(compile in Compile, compile in Test)