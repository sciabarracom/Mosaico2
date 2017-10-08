import ammonite.ops._
import $file.lib.Cmd


@main def ansible() {
  val inventory = sys.props("ansible.inventory")
  val script = sys.props("ansible.script")
  Cmd.ansible(inventory, script)
}

@main def terraform(args: String*) {
  Cmd.terraform(args)
}
