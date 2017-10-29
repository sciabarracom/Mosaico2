import ammonite.ops._
import collection.JavaConversions._
import scala.util.Try

import $file.lib.SSH
import $file.lib.EC2

val defaultUser = Option(sys.props("aws.ssh.user")).getOrElse("centos")

val defaultTag = (Option(sys.props("aws.tag.name")).getOrElse("Application"),
           Option(sys.props("aws.tag.value")).getOrElse("Mosaico"))

def doSsh(tag: (String,String), capture: Boolean, args: Seq[String]): Seq[String] = {
   val instances = EC2.runningTaggedInstances(tag)
   val exec = SSH.Ssh(defaultUser, capture, EC2.ipAddresses(instances))
   exec(args: _*)
}

@main def awssh(args: String*): Unit = {
  if(args.head(0) == '@') {
    args.head.tail.split(",").foreach {
      host =>
        //print(s"[${host}] ")
        val tag = "Name" -> host
        doSsh(tag, false, args.tail)
    }
  } else {
    //println(args)
    doSsh(defaultTag, false, args)
  }
}

@main def list() {
  val instances = EC2.withTag(EC2.instances(), defaultTag)
  val out = instances map { x =>
    val tags = x.getTags.toList.map {
      y =>  s"${y.getKey}=${y.getValue}"
    }.mkString("[", ",", "]")
    s"${tags}\t${x.getState.getName}\t${x.getPublicIpAddress}\t${x.getPublicDnsName}\t${x.getPrivateIpAddress}\t${x.getPrivateDnsName}"
  }
  println(out.mkString("\n"))
}

@main def stop() {
  val instances = EC2.runningTaggedInstances(defaultTag)
  EC2.stopInstances(instances)
}

@main def start() {
  val instances = EC2.withTag(EC2.instances(), defaultTag)
  EC2.startInstances(instances)
}

// util

/**
Execute ssh take 1 single string parameter
*/
def awssh1(arg: String) = awssh(arg.split(" "): _*)

/**
Execute ssh on an host by name and capture output
takes 2 parameters
*/
def awssh2(name:String,  arg: String) = {
  doSsh("Name" -> name, true, arg.split(" ")).head
}
