import $ivy.`com.amazonaws:aws-java-sdk:1.11.22`

import ammonite.ops._
import collection.JavaConverters._

import com.amazonaws.services.ec2._
import com.amazonaws.services.ec2.model._
import com.amazonaws.regions.{Region=>Ec2Region,Regions=>Ec2Regions}

// instances

val region = Option(sys.props("mosaico.region")).getOrElse("us-east-1")

// client
val ec2 = new AmazonEC2Client()
val regions = Ec2Regions.fromName(region)
ec2.setRegion(Ec2Region.getRegion(regions))

// instances
def instances() = (for {
  reservation <- ec2.describeInstances.getReservations.asScala
  instance <- reservation.getInstances.asScala
} yield {
  instance
}).toSeq

def running(insts: Seq[Instance]) = insts.filter {
   _.getState.getName == "running"
}

def hasTag(tags: Seq[Tag], kv: (String,String)) = {
  tags
   .map(t => t.getKey == kv._1 && t.getValue == kv._2)
   .reduce(_ || _)
}

def withTag(insts: Seq[Instance], kv: (String,String)) =
  insts.filter { x =>
   hasTag(x.getTags.asScala.toSeq, kv)
  }

def runningTaggedInstances(tag: (String,String)) =
  withTag(running(instances), tag)

def ipAddresses(instances: Seq[Instance]) = instances map {
   x=> x.getPrivateIpAddress -> x.getPublicIpAddress
}

def dnsNames(instances: Seq[Instance]) = instances map {
  x=> x.getPrivateDnsName -> x.getPublicDnsName
}

def startInstances(instances: Seq[Instance]) = {
  val ids = instances.map { _.getInstanceId }
  val req = new StartInstancesRequest().withInstanceIds(ids: _*)
  ec2.startInstances(req)
}

def stopInstances(instances: Seq[Instance]) = {
  val ids = instances.map { _.getInstanceId }
  val req = new StopInstancesRequest().withInstanceIds(ids: _*)
  ec2.stopInstances(req)
}
