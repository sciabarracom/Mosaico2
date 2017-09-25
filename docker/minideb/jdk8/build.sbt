prpLookup += baseDirectory.value.getParentFile -> "minideb"

imageNames in docker := Seq(ImageName(prp.value("jdk8")))

val dbase = (project in file("..")/"dbase").enablePlugins(MosaicoDockerPlugin)

dockerfile in docker := {
  Def.sequential(
    download.toTask(s" @jdk8.url jdk.tgz Cookie: oraclelicense=accept-securebackup-cookie"),
    untar.toTask(" jdk.tgz usr -.*/db/.* -.*/lib/missioncontrol/.* -.*/lib/visualvm/.* -.*/include/.* -.*/man/.* -.*/plugin/.* -.*src.zip$")
  ).value
  val basedir = baseDirectory.value
  new Dockerfile {
    from( (docker in dbase).value.toString)
    add(basedir/"usr", "/usr/")
    runRaw("ln -sf /usr/jdk* /usr/java ; chmod +x /usr/java/bin/*")
    env("JAVA_HOME", "/usr/java")
    env("PATH", "/bin:/sbin:/usr/bin:/usr/sbin:/usr/java/bin")
  }
}
