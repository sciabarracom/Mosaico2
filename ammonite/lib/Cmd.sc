import ammonite.ops._


def ansible(inventory: String, script: String) = {
  val ansibleDir = pwd/up/'ansible
  %("ansible-playbook", "-i", inventory, script)(ansibleDir)
}

def terraform(args: Seq[String]) = {
  implicit val wd = pwd/up/'terraform
  %("terraform", args)
}
