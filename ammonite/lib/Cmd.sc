import ammonite.ops._

def ansible(inventory: String, script: String) = {
  val ansibleDir = pwd/up/'ansible
  %("ansible-playbook", "-i", inventory, script)(ansibleDir)
}

def terraform(args: Seq[String]) = {
  val terraformDir = pwd/up/'terraform
  %("terraform", args)(terraformDir)
}
