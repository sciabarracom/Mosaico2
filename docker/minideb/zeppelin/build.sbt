prpLookup += baseDirectory.value.getParentFile -> "minideb"

imageNames in docker := Seq(ImageName(prp.value("zeppelin")))

val zeppelin = (project in file("..") / "spark").enablePlugins(MosaicoDockerPlugin)

dockerfile in docker := {
  download.toTask(s" @zeppelin.url %zeppelin.url").value
  val base = baseDirectory.value
  new Dockerfile {
    from((docker in spark).value.toString)
    add(base / "run.sh", "/services/zeppelin/run")
    add(base / zeppelin, "/usr")
    runRaw(
      """|ln -sf /usr/zeppelin-* /usr/zeppelin ;
         |rm -Rf /services/spark ;
         |chmod +x /services/zeppelin/run
         |""".stripMargin.replace('\n', ' '))
    add(base / "zeppelin-site.xml", "/usr/zeppelin/conf")
  }
}
