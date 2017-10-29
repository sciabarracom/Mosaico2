import ammonite.ops._
import $file.lib.EC2
import $exec.aws

//import $ivy.`org.json4s::json4s-native:3.5.3`
//import org.json4s._
//import org.json4s.native.JsonMethods._

val defaultUser = Option(sys.props("aws.ssh.user")).getOrElse("centos")

/*
val stack = "docker-compose"
*/
def copyYaml(file: String) = {
 val deployDir = pwd/up/'deploy
 val master = EC2.ipAddresses(EC2.runningTaggedInstances("Name" -> "master")).head._2
 val src = (deployDir/s"${file}").toIO.getAbsolutePath
 val cmd = Seq("scp", "-o", "StrictHostKeyChecking=no",
                src, s"${defaultUser}@${master}:${file}")
 %(cmd)(deployDir)
}

@main def deploy(stack: String="docker-compose") {
  copyYaml(stack+".yml")
  awssh1(s"@master docker stack deploy -c ${stack}.yml ${stack}")
}

/*
json.split("\n").map(parse(_)).map(render(_)).map(pretty(_)).foreach(println)
*/
def serviceByNode(stack: String="docker-compose") = {
   // Running 3 hours ago|docker-compose_zeppelin.1|ip-10-0-0-7.ec2.internal
   val ipByNode = awssh2("master", s"""docker stack ps ${stack} --format='{{.CurrentState}}|{{.Name}}|{{.Node}}'""")
   // Array("Running 3 hours ago", "docker-compose_spark-worker.1", "ip-10-0-0-7.ec2.internal"
   val ipByNode1 = ipByNode.split("\n").filter(_.startsWith("Running")).map(_.split("\\|"))
   // "docker-compose_spark-master" -> Array(("docker-compose_spark-master", ("1", "ip-10-0-0-6.ec2.internal")))
   val ipByNodeMap = ipByNode1.map{ a =>
       val b = a(1).split("\\.") ;
       b.head.split("_").tail.head -> (a(2) -> (b.tail.head.toInt -1))
    }.groupBy(_._1)
   // cleaning up
   ipByNodeMap.mapValues(_.map(_._2))
}

@main def services(stack: String = "docker-compose") = {
  println("registry\t1/1\t*5000->5000/tcp")
  println("jenkins \t1/1\t*8080->8080/tcp")
  println(s"Stack: ${stack}")
  // docker-compose_zeppelin|*:4040->4040/tcp,*:8022->22/tcp,*:8081->8080/tcp
  val svc = awssh2("master", s"docker stack services ${stack} --format='{{.Name}}|{{.Replicas}}~{{.Ports}}'")
  val svcMap = svc.split("\n").map(_.split("\\|")).map(a => a(0) -> a(1)).toMap
  for {
    (k,v) <- svcMap
  } {
    val kk = k.split("_").tail.head
    val vv = v.split("~")
    println(s"${kk}\t${vv(0)}\t${vv(1)}")
  }
}

/*
val services = Seq("8081:zeppelin", "8082:spark-master")
val port = "8081:zeppelin:8081"
val stack = "docker-compose"
proxy("docker-compose", "8081:zeppelin", "8082:spark-master", "8083:spark-worker")
*/
@main def proxy(stack: String, services: String*) = {

  val masterIp = EC2.runningTaggedInstances("Name" -> "master").head.getPublicDnsName

  //println(s"stack: ${stack}")
  val map = serviceByNode(stack)
  //println(map)
  println("Proxy:")
  println("http://localhost:8080 => jenkins:8080")
  println("http://localhost:5000 => registry:5000")
  val forward = for {
     port <- services
     a = port.split(":")               // split 8080:zeppelin:8080
     in = a(0)                         // 8080
     svc = a(1)                        // zeppelin
     out = if(a.size==3) a(2) else a(0) // 8080
     (target, offset) <-  map(svc)     // (0, "ip-10-0-0-6.ec2.internal")
  } yield {
    println(s"http://localhost:${out.toInt+offset} => ${svc}.${offset}:${in}")
    s"-L${in.toInt + offset}:${target}:${out.toInt}"
  }
  val cmd = s"ssh -o StrictHostKeyChecking=no "+
     s"-L8080:localhost:8080 -L5000:localhost:5000 "+
     s"${forward.mkString(" ")} ${defaultUser}@${masterIp} "+
     s"echo 'Press Enter to Exit:'; read -p ''"

  %(cmd.split(" ").toSeq)(pwd)

}
