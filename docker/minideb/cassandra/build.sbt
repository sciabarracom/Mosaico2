prpLookup += baseDirectory.value.getParentFile -> "minideb"

imageNames in docker := Seq(ImageName(prp.value("cassandra")))

val jdk8 = (project in file("..") / "jdk8").enablePlugins(MosaicoDockerPlugin)

dockerfile in docker := {
  val cassandra = file(url(prp.value("cassandra.url")).getFile).getName
  download.toTask(s" @cassandra.url %cassandra.url").value
  val base = baseDirectory.value
  new Dockerfile {
    from((docker in jdk8).value.toString)
    add(base / cassandra, "/usr")
    add(base / "run.sh", "/services/cassandra/run")
    runRaw("ln -sf /usr/apache-cassandra-* /usr/cassandra ; chmod +x /services/cassandra/run")
  }
}
