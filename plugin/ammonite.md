# MosaicoAmmonitePlugin

- `amm` launches ammonite
- `amm script.sc` execute a script from the project directory

By default is has no predef, and it is silent

You can specify the following settings:

- `ammPredef := Some("predef.sc")` to use the `predef.sc`
- `ammVersion := "1.0.2" -> "2.12.2"` to use a specific version of ammonite/scala (default is 1.0.2 and 2.12.2)

It will also setup system properties from a configuration file, both hocon and property files with [profile support](config.md)

You can select the configuration file to use with

`prpLookup += baseDirectory.value -> "config"
 hoconLookup +=  baseDirectory.value -> "config"
`
It will look for to `config` files in the parent directory (with extension depending on the profile) and will use them to set System properties (accessible with sys.props )
