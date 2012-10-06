DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export CLASSPATH="$DIR/../target/lib/*:$DIR/../target/*"
export JAVA_OPTS="${JAVA_OPTS} -DXmx4096M"
scala $WORDNIK_OPTS -cp $CLASSPATH "$@" src/main/scripts/ScalatraServerGenerator.scala -DfileMap=src/main/templates/spec-files
