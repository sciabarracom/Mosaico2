import ammonite.ops._
import scala.util.Try
import $file.lib.Cmd


@main def ansible() {
  val inventory = sys.props("ansible.inventory")
  val script = sys.props("ansible.script")
  Try(Cmd.ansible(inventory, script))
}

@main def terraform(args: String*) {
   Try(Cmd.terraform(args))
}
