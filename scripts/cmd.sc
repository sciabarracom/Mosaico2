import ammonite.ops._
import scala.util.Try
import $file.lib.Cmd
import $file.lib.EC2
import $exec.aws

val defaultUser = Option(sys.props("aws.ssh.user")).getOrElse("centos")

@main def ansible(args: String*) {
  val inventory = sys.props("ansible.inventory")
  val script = sys.props("ansible.script")
  Try(Cmd.ansible(inventory, script, args))
}

@main def terraform(args: String*) {
   if(args.size == 0)
     Try(Cmd.terraform(Seq("apply")))
   else
     Try(Cmd.terraform(args))
}

@main def jenkins(args: String*) {
  val command = "sudo docker exec `"+
      "sudo docker ps -f name=jenkins | awk '/jenkins/ { print $1 }'" +
      "` cat /var/jenkins_home/secrets/initialAdminPassword"

  //println(s"Jenkins Server: http://${EC2.runningTaggedInstances("Name" -> "master").head.getPublicDnsName}:8080")
  val pass = awssh2("master", command)
  println(s"Jenkins Initial Password: ${pass}")
}

@main def docker(args: String*) {
  val args1 = "@master sudo docker".split(" ").toSeq++args
  awssh(args1: _*)
}
