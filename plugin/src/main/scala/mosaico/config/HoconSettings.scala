package mosaico.config

import java.util.Properties
import mosaico.common.{MiscUtils, FileUtils}
import sbt.Keys._
import sbt._
import com.typesafe.config._


/**
  * Settings for hocon see documentation for details
  *
  */
trait HoconSettings extends FileUtils with MiscUtils {
  this: AutoPlugin =>

  trait HoconKeys {
    lazy val hocon = settingKey[Config]("Hocon Config")
    lazy val hoconLookup = settingKey[Seq[(File, String)]]("Where to lookup for conf files")
  }

  import MosaicoConfigPlugin.autoImport._
  import scala.collection.JavaConverters._


  val hoconExtensions = Seq(
    "dist.conf",
    "conf",
    "local.conf") ++
    Option(System.getProperty("profile")).toSeq.map { x=> s"${x}.conf" }

  //import $ivy.`com.typesafe:config:1.3.1`
  lazy val hoconTask = hocon := {
    var conf: Config = ConfigFactory.empty
    try {
      val loaded = for {
        (confDir, confPrefix) <- hoconLookup.value
        confExt <- hoconExtensions
        confName = s"${confPrefix}.${confExt}"
        confFile = confDir / confName
      } {
        //println(s"looking for ${confFile}")
        if (confFile.exists) {
          println(s"loading ${confFile.getName}")
          conf = ConfigFactory.parseFile(confFile).withFallback(conf)
          val filename = confFile.getAbsolutePath
          trace("conf", s"loaded ${filename}")
        }
      }
      conf
    } catch {
      case _: Throwable => conf
    }
  }


  val hoconSettings = Seq(
    hoconLookup := Seq(baseDirectory.value -> "mosaico")
    , hoconTask
  )
}
