val scripts = project in file(".")

enablePlugins(MosaicoAmmonitePlugin,MosaicoConfigPlugin)

ammPredef := Some("predef.sc")

prpLookup += baseDirectory.value.getParentFile -> "config"

addCommandAlias("aws", "amm aws.sc")
addCommandAlias("awssh", "amm aws.sc awssh")

addCommandAlias("cmd", "amm cmd.sc")
addCommandAlias("terraform", "amm cmd.sc terraform")
addCommandAlias("ansible", "amm cmd.sc ansible")
addCommandAlias("jenkins", "amm cmd.sc jenkins")

addCommandAlias("docker", "amm cmd.sc docker")
addCommandAlias("deploy", "amm cmd.sc docker stack deploy -c docker-compose.yml stack")
