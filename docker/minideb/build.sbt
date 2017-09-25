lazy val dbase = project
  .enablePlugins(MosaicoDockerPlugin)

lazy val jdk8 = project
  .enablePlugins(MosaicoDockerPlugin)

lazy val hadoop = project
  .enablePlugins(MosaicoDockerPlugin)

lazy val spark = project
  .enablePlugins(MosaicoDockerPlugin)

lazy val cassandra = project
  .enablePlugins(MosaicoDockerPlugin)

lazy val zeppelin = project
  .enablePlugins(MosaicoDockerPlugin)

lazy val sbt13 = project
  .enablePlugins(MosaicoDockerPlugin)


lazy val minideb = (project in file(".")).
  aggregate(dbase, jdk8, hadoop, spark, cassandra, zeppelin, sbt13)
