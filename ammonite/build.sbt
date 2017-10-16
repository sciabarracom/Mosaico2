val cloud = project in file(".")

enablePlugins(MosaicoAmmonitePlugin,MosaicoConfigPlugin)

ammPredef := Some("predef.sc")

prpLookup += baseDirectory.value.getParentFile -> "config"

addCommandAlias("aws", "amm aws.sc")

addCommandAlias("awssh", "amm aws.sc ssh")

addCommandAlias("cmd", "amm cmd.sc")

addCommandAlias("terraform", "amm cmd.sc terraform")

addCommandAlias("ansible", "amm cmd.sc ansible")

addCommandAlias("jenkins", "amm cmd.sc jenkins")

