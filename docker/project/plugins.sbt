val sbtMosaicoVer = "0.4"

addSbtPlugin("com.sciabarra" % "mosaico-sbt" % sbtMosaicoVer)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.4.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.8.5")

scalacOptions += "-deprecation"
