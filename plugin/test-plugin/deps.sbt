lazy val abuild = (project in file("./abuild")).enablePlugins(MosaicoDockerPlugin)

lazy val common = (project in file("./common")).enablePlugins(MosaicoDockerPlugin)

lazy val script = (project in file("./script")).enablePlugins(MosaicoConfigPlugin,MosaicoAmmonitePlugin)

addCommandAlias("dockers", "; abuild/docker ; common/docker ")
