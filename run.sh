#!/bin/sh

function run() {
  mvn -Dmaven.tomcat.port=8080 tomcat7:run -pl *-server -am -Denv=dev &
  mvn gwt:codeserver -pl *-client -am
#  open http://localhost:8080
}

function cleanup {
  killall java
}

run
{ set +x; } 2>/dev/null
trap cleanup EXIT




