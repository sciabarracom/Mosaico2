version := "0.4"

sbtPlugin := true

name := "mosaico-sbt"

organization := "com.sciabarra"

scalaVersion := "2.10.6"

scalacOptions += "-feature"

libraryDependencies ++= Seq(
  "ch.qos.logback"               %  "logback-classic"                  % "1.1.3"   % "test;compile"
  , "org.scalatest"              %% "scalatest"                        % "2.2.4"   % "test"
  , "org.rauschig"               %  "jarchivelib"                      % "0.7.0"
  , "com.typesafe.akka"          %% "akka-testkit"                     % "2.3.9"   % "test"
  , "com.typesafe.scala-logging" %% "scala-logging-slf4j"              % "2.1.2"
  , "com.typesafe.akka"          %% "akka-actor"                       % "2.3.9"
  , "com.typesafe.akka"          %% "akka-slf4j"                       % "2.3.9"
  , "com.typesafe.akka"          %  "akka-stream-experimental_2.10"    % "2.0.4"
  , "com.typesafe.akka"          %  "akka-http-core-experimental_2.10" % "2.0.4"
  , "com.typesafe.akka"          %  "akka-http-experimental_2.10"      % "2.0.4"
  , "com.typesafe"               %  "config"                           % "1.3.1"
  )

addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.4.1")

isSnapshot := version.value.endsWith("-SNAPSHOT")

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

//publishArtifact in (Compile, packageDoc) := false

licenses := Seq("Apache-style" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

homepage := Some(url("http://sciabarra.com"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/sciabarracom/Mosaico2"),
    "scm:git@github.com:sciabarracom/Mosaico2.git"
  )
)

developers := List(
  Developer(
    id    = "sciabarra",
    name  = "Michele Sciabarra",
    email = "michele@sciabarra.com",
    url   = url("http://michele.sciabarra.com")
  )
)

sonatypeProfileName := "com.sciabarra"
