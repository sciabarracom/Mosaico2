prpLookup += baseDirectory.value.getParentFile -> "alpine"

imageNames in docker := Seq(ImageName(prp.value("abuild")))

dockerfile in docker := {
  val buildSh = (baseDirectory.value / "build.sh")
  new Dockerfile {
    from("alpine:3.5")
    runRaw("apk update")
    runRaw("apk -U add alpine-sdk git curl sudo bash python2 python2-dev py2-pip nodejs nodejs-dev file linux-headers")
    runRaw("pip install --upgrade setuptools")
    runRaw("pip install pypi-show-urls")
    runRaw(
      s"""
         |adduser -D packager &&
         |addgroup packager abuild &&
         |mkdir -p /var/cache/distfiles /home/packager/.abuild &&
         |chown packager:packager /home/packager/.abuild/ &&
         |echo "packager    ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers &&
         |yes | su - packager -c 'abuild-keygen -a -i ; echo PACKAGER_PRIVKEY=\"/home/packager/y\" >/home/packager/.abuild/abuild.conf'
         |""".stripMargin('|').replace('\n', ' '))
    user("packager")
    add(buildSh, "/home/")
    entryPoint("/bin/bash", "/home/build.sh")
  }
}
