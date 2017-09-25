lazy val abuild = project
  .enablePlugins(MosaicoDockerPlugin)

lazy val abase = project
  .enablePlugins(MosaicoDockerPlugin)

lazy val nginx = project
  .enablePlugins(MosaicoDockerPlugin)

lazy val python2 = project
  .enablePlugins(MosaicoDockerPlugin)

lazy val django = project
  .enablePlugins(MosaicoDockerPlugin)

lazy val alpine = (project in file(".")).
  aggregate(abuild, abase, nginx, python2, django)
