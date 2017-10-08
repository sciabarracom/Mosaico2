watchTransitiveSources := Seq()

enablePlugins(MosaicoAmmonitePlugin,MosaicoConfigPlugin)

ammPredef := Some("predef.sc")

prpLookup += baseDirectory.value.getParentFile -> "config"

addCommandAlias("aws", "amm aws.sc")

addCommandAlias("awssh", "amm aws.sc ssh")

addCommandAlias("cmd", "amm cmd.sc")

addCommandAlias("terraform", "amm cmd.sc terraform --args")

addCommandAlias("ansible", "amm cmd.sc ansible")
