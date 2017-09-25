prpLookup += baseDirectory.value.getParentFile -> "alpine"

imageNames in docker := Seq(ImageName(prp.value("python2")))

val abase = (project in file("..") / "abase")
  .enablePlugins(MosaicoDockerPlugin)

dockerfile in docker := {
  (docker in abase).value

  val apk = Def.sequential(
    alpineBuild.toTask(" @abuild uwsgi.sh uwsgi.apk")
  ).value

  new Dockerfile {
    from(prp.value("abase"))
    runRaw(s"apk add sqlite python2 py2-pip py-psycopg2 py-pillow")
    copy(apk, "/tmp/")
    runRaw("apk add --allow-untrusted /tmp/*.apk  && rm /tmp/*.apk")
    env("UWSGI_PROCESSES", "4")
    env("UWSGI_THREADS", "2")
  }
}
