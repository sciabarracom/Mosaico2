import ammonite.main.Router.main

println(sys.props("test.description"))
print(s"[${sys.props("test.location")}] ")
println(sys.props("test.greet"))
