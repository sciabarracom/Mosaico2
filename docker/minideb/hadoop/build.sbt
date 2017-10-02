prpLookup += baseDirectory.value.getParentFile -> "minideb"

imageNames in docker := Seq(ImageName(prp.value("hadoop")))

val jdk8 = (project in file("..")/"jdk8").enablePlugins(MosaicoDockerPlugin)

dockerfile in docker := {
  download.toTask(s" @hadoop.url hadoop.tgz").value
  val base = baseDirectory.value
  new Dockerfile {
    from((docker in jdk8).value.toString)
    add(base/"hadoop.tgz", "/usr")
    runRaw("ln -sf /usr/hadoop-* /usr/hadoop")
    env("HADOOP_PREFIX", "/usr/hadoop")
    runRaw(s"""
           |install_packages openssh-server openssh-client ;
           |ssh-keygen -f /etc/ssh/ssh_host_rsa_key -N '' ;
           |ssh-keygen -f /root/.ssh/id_rsa -N '' ;
           |cp /root/.ssh/id_rsa.pub /root/.ssh/authorized_keys ;
           |echo set /files/etc/ssh/ssh_config/Host/StrictHostKeyChecking no | augtool -s ;
           |echo set /files/etc/ssh/sshd_config/PermitRootLogin yes | augtool -s ;
           |""".stripMargin.replace('\n', ' '))
    add(base/"run.sh", "/services/hadoop/run")
    runRaw("mkdir /data ; chmod +x /services/hadoop/run /usr/hadoop/bin/* /usr/hadoop/sbin/*")
    expose(50010,50020,50070,50075,50090)
  }
}
