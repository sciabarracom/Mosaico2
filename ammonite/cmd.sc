import ammonite.ops._
import scala.util.Try
import $file.lib.Cmd
import $exec.aws

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
  ssh(command.split(" "): _*)
}

@main def services() {
  Cmd.services("centos", "docker-compose.yml")
  ssh("@master")
}
