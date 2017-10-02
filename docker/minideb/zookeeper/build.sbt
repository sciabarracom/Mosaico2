prpLookup += baseDirectory.value.getParentFile -> "minideb"

imageNames in docker := Seq(ImageName(prp.value("zookeeper")))

val jdk8 = (project in file("..")/"jdk8").enablePlugins(MosaicoDockerPlugin)

dockerfile in docker := {
  val filename = file(url(prp.value("zookeeper.url")).getFile).getName
  download.toTask(s" @zookeeper.url %zookeeper.url").value
  val base = baseDirectory.value
  new Dockerfile {
    from((docker in jdk8).value.toString)
    add(base/filename, "/usr")
    runRaw("ln -sf /usr/zookeeper-* /usr/zookeeper ; chmod +x /usr/zookeeper/bin/*")
  }
}
