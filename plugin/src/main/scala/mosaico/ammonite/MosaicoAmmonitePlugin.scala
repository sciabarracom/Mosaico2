package mosaico.ammonite

import java.io.File

import sbt._
import Keys._
import sbt.plugins.JvmPlugin
import mosaico.config.MosaicoConfigPlugin

object MosaicoAmmonitePlugin
  extends AutoPlugin {

  object autoImport {
    val ammVersion = settingKey[(String, String)]("Ammonite Version")
    val ammPredef = settingKey[Option[String]]("Ammonite predef")
    val amm = inputKey[Unit]("Ammonite Launch Command")
  }

  import autoImport._
  import MosaicoConfigPlugin.autoImport._
  import scala.collection.JavaConversions._

  val ammTask = amm := {
    val args = Def.spaceDelimited("<arg>").parsed
    val home = baseDirectory.value
    val classpath = (fullClasspath in Compile).value.files

    val props1 = hocon.value.resolve().entrySet.toSeq.map {
      x => s"-D${x.getKey}=${x.getValue.unwrapped.toString}"
    }

    val props2 = prp.value.map {
      x => s"-D${x._1}=${x._2}"
    }

    val predef: Seq[String] = if (ammPredef.value.isEmpty) Seq()
    else Seq("-p", ammPredef.value.getOrElse("--no-default-predef"))

    val jvmOpts = props1 ++ props2 ++ Seq("-cp"
      , classpath.map(_.getAbsolutePath).mkString(File.pathSeparator)
      , "ammonite.Main")

    val forkArgs =
      Seq("-s", "-h", home.getAbsolutePath) ++
        predef ++ args

    val forkOpts = ForkOptions(
      runJVMOptions = jvmOpts,
      connectInput = true,
      outputStrategy = Some(StdoutOutput),
      envVars = Map.empty,
      workingDirectory = Some(home))

    Fork.java(forkOpts, forkArgs)
  }

  override val projectSettings = Seq(
    ammVersion := "1.0.2" -> "2.12.2"
    , scalaVersion := ammVersion.value._2
    , ammPredef := None
    , libraryDependencies += "com.lihaoyi" % s"ammonite_${ammVersion.value._2}" % ammVersion.value._1
    , ammTask
  )

  override def requires =
    JvmPlugin && MosaicoConfigPlugin
}
