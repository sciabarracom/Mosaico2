import ammonite.ops._

class Ssh(user: String, ips: Seq[(String,String)])
{
  def apply(cmd: Any*): Unit = {

    val args = cmd map { obj =>
       obj match {
          case s: Symbol => s.name
          case x: Any => x.toString
       }
    }

    implicit val wd = pwd

    ips map { ip =>
        val tgt = s"${user}@${ip._2}"

        val sshArgs: Seq[String] = Seq(
          "-o", "StrictHostKeyChecking=no",
          tgt
        ) ++ args

        println(s"[${ip._1}] ${args.mkString(" ")}")
        %ssh(sshArgs)
    }
  }
}

object Ssh {
  def apply(user: String, ips: Seq[(String,String)]) = new Ssh(user, ips)
}
