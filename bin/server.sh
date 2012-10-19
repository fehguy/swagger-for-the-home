#!/bin/bash
echo "" > classpath.txt
for file in `ls target/lib/*.jar`;
        do echo -n '' >> classpath.txt;
        echo -n $file >> classpath.txt;
        echo -n ':' >> classpath.txt;
done
for file in `ls target/*.jar`;
        do echo -n '' >> classpath.txt;
        echo -n $file >> classpath.txt;
        echo -n ':' >> classpath.txt;
done
export CLASSPATH=$(cat classpath.txt)
export JAVA_OPTS="${JAVA_OPTS} -DXmx4096M"

scala $WORDNIK_OPTS -cp $CLASSPATH "$@" src/main/scripts/ScalatraServerGenerator.scala -DfileMap=src/main/templates/spec-files
cp -r src/main/templates/scalatra/admin server/src/main/webapp/admin