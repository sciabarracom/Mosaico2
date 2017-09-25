prpLookup += baseDirectory.value.getParentFile -> "minideb"

imageNames in docker := Seq(ImageName(prp.value("dbase")))

dockerfile in docker := {
  val basedir = baseDirectory.value
  new Dockerfile {
    from("bitnami/minideb:jessie")
    runRaw(
      s"""|install_packages curl sudo augeas-lenses augeas-tools daemontools telnet vim inetutils-ping
          |""".stripMargin.replace('\n', ' '))
    cmd("/usr/bin/svscan", "/services/")
  }
}
