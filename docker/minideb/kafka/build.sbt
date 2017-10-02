prpLookup += baseDirectory.value.getParentFile -> "minideb"

imageNames in docker := Seq(ImageName(prp.value("kafka")))

val jdk8 = (project in file("..")/"jdk8").enablePlugins(MosaicoDockerPlugin)

dockerfile in docker := {
  val filename = file(url(prp.value("kafka.url")).getFile).getName
  download.toTask(s" @kafka.url %kafka.url").value
  val base = baseDirectory.value
  new Dockerfile {
    from((docker in jdk8).value.toString)
    add(base/filename, "/usr")
    runRaw("ln -sf /usr/kafka_* /usr/kafka ; chmod +x /usr/kafka/bin/*")
    add(base/"run.sh", "/services/kafka/run")
    runRaw("chmod +x /services/kafka/run")
    expose(2181,2888,3888,9092)
  }
}
