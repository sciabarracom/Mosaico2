import ammonite.ops._
import scala.util.Try
import $file.lib.Cmd
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
  val command = "@master sudo docker exec `"+
      "sudo docker ps -f name=jenkins | awk '/jenkins/ { print $1 }'" +
      "` cat /var/jenkins_home/secrets/initialAdminPassword"
  println("Jenkins Initial Password")
  awssh(command.split(" "): _*)
}

@main def docker(args: String*) {
  Cmd.copyCompose(defaultUser, "docker-compose.yml")
  val args1 = "@master sudo docker".split(" ").toSeq++args
  awssh(args1: _*)
}
