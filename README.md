### Swagger for the home

Project to make an API for my house.  Based on the following:

* [Phidgets SBC](http://www.phidgets.com/) running Debian

* [Swagger Scalatra](https://www.scalatra.org).  I was using nodejs first but the phidget support isn't great for it (yet).

### Getting started

The Phidget library isn't in a maven repo, so pull it from here:

```
http://www.phidgets.com/downloads/libraries/phidget21jar.zip
```

and install it in your local maven repo like such:

```
mvn install:install-file -DgroupId=com.phidgets -DartifactId=phidget -Dversion=2.1.0 -Dpackaging=jar -Dfile=phidget21.jar
```

You can build the assembly like this:

```
cd server
sbt assembly
```

You can run the server like this:

```
cd server/target
java -jar sfth-assembly-0.1.0-SNAPSHOT.jar
```

This starts up the server locally.  You can now hit the API via included swagger-ui:

```
http://localhost:8080/admin
```

### Making it yours

This server uses the [Swagger Codegen](https://github.com/wordnik/swagger-codegen) project to generate the scalatra server.  You can 
repeat this process by running the `./bin/server.sh` command--the codegen will read from `src/main/scripts/ScalatraServerGenerator.scala`,
which in turn uses templates inside `src/main/templates/scalatra` for the code generation.  The swagger spec files located in `src/main/templates/spec-files`.

When running the `./bin/server.sh` command, the codegen will read the spec files and generate a server from them.  That means the server
itself is defined by the spec files, and the code is generated from them.  To avoid overwriting the actual implementation on every api change,
the codegen delegates the logic to a service tier, which is also defined in the templates.
