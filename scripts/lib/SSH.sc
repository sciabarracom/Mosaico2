import ammonite.ops._

class Ssh(user: String, capture: Boolean, ips: Seq[(String,String)])
{
  def apply(cmd: Any*): Seq[String] = {

    val args = cmd map { obj =>
       obj match {
          case s: Symbol => s.name
          case x: Any => x.toString
       }
    }

    implicit val wd = pwd

    ips flatMap { ip =>
        val tgt = s"${user}@${ip._2}"

        val sshArgs: Seq[String] = Seq(
          "-o", "StrictHostKeyChecking=no",
          tgt
        ) ++ args

        if(capture) {
          Some( (%%ssh(sshArgs)).out.string )
        } else {
          println(s"[${ip._1}] ${args.mkString(" ")}")
          try {
             %ssh(sshArgs)
          } catch {
           case ex: ShelloutException => println(ex.getMessage)
          }
          None
        }
    }
  }
}

object Ssh {
  def apply(user: String, capture: Boolean, ips: Seq[(String,String)]) = new Ssh(user, capture, ips)
}
