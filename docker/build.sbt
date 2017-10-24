name := "mosaico"

version := "0.4-SNAPSHOT"

scalaVersion := "2.11.8"

enablePlugins(SupportPlugin,MosaicoConfigPlugin)

lazy val alpine = project in file("alpine")

lazy val minideb = project in file("minideb")

lazy val docker  = (project in file(".")).aggregate(minideb, alpine)
