import ammonite.ops._
import $file.EC2

def ansible(inventory: String, script: String, args: Seq[String]) = {
  val ansibleDir = pwd/up/'ansible
  %("ansible-playbook", "-i", inventory, script, args)(ansibleDir)
}

def terraform(args: Seq[String]) = {
  val terraformDir = pwd/up/'terraform
  %("terraform", if(args.size==0) Seq("apply") else args)(terraformDir)
}

def copyCompose(user: String, file: String) = {
 val deployDir = pwd/up/'deploy
 val master = EC2.ipAddresses(EC2.runningTaggedInstances("Name" -> "master")).head._2
 val src = (deployDir/file).toIO.getAbsolutePath
 val cmd = Seq("scp", src, s"${user}@${master}:${file}")
 %(cmd)(deployDir)
}
