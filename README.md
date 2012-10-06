### Swagger for the home

Project to make an API for my house.  Based on the following:

* [Phidgets SBC](http://www.phidgets.com/) running Debian

* [Swagger Scalatra](https://www.scalatra.org).  I was using nodejs first but the phidget support isn't great for it (yet).

### Getting started

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

