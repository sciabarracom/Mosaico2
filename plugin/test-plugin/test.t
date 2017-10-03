Cleanup

  $ docker rmi -f sciabarra/test-abuild:1 sciabarra/test-common:1 >/dev/null 2>/dev/null
  $ cd $TESTDIR
  $ rm common/target/*.apk >/dev/null

Building

  $ ./sbt -no-colors abuild/docker common/docker 2>/dev/null  | grep Tagging
  [info] Tagging image * with name: sciabarra/test-abuild:1 (glob)
  [info] Tagging image * with name: sciabarra/test-abuild:1 (glob)
  [info] Tagging image * with name: sciabarra/test-common:1 (glob)

Checking images

  $ docker images | grep sciabarra/test
  sciabarra/test-common   1 .* (re)
  sciabarra/test-abuild   1 .* (re)

Checking package

  $ ls common/target/*.apk
  common/target/daemontools.apk
  $ ./sbt "amm hello.sc" 2>/dev/null | grep Hello
  Hello Properties and Hocon
  [earth] Hello, Earth.
  $ ./sbt -Dprofile=mars "amm hello.sc" 2>/dev/null | grep Hello
  Hello Properties and Hocon
  [mars] Hello, Mars.
