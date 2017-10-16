prpLookup += baseDirectory.value.getParentFile -> "minideb"

imageNames in docker := Seq(ImageName(prp.value("spark")))

val hadoop = (project in file("..")/"hadoop")
  .enablePlugins(MosaicoDockerPlugin)

dockerfile in docker := {
    val spark = file(url(prp.value("spark.url")).getFile).getName
    Def.sequential(
      download.toTask(s" @spark.url %spark.url"),
      download.toTask(s" @spark.slf4j.url slf4j-api.jar")
    ).value
    val base = baseDirectory.value
    new Dockerfile {
      from((docker in hadoop).value.toString)
      add(base/spark, "/usr")
      runRaw("ln -sf /usr/spark-* /usr/spark")
      add(base/"slf4j-api.jar", "/usr/spark/jars")
      add(base/"spark-shell.sh", "/usr/bin/spark-shell")
      runRaw("chmod +x /usr/spark/bin/* /usr/spark/sbin/*")
      add(base/"log4j.properties", "/usr/spark/conf/log4j.properties")
      add(base/"run.sh", "/services/spark/run")
      //add(base/"spark-defaults.conf", "/usr/spark/conf")
      //runRaw("echo 'System.exit(0)' | /usr/bin/spark-shell")
    }
}
