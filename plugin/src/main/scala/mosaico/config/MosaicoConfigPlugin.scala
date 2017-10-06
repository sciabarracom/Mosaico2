package mosaico.config

import sbt.Keys._
import sbt._
import sbt.plugins.JvmPlugin

/**
  * Plugin for configurations, see documentation for details.
  */
object MosaicoConfigPlugin
  extends AutoPlugin
    with PropertySettings
    with HoconSettings {

  override def requires = JvmPlugin

  object autoImport extends
    PropertyKeys
    with HoconKeys


  override val projectSettings =
    propertySettings ++
    hoconSettings
}
