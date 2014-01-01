### Swagger for the home

Project to make an API for my house.  Based on the following:

* [Raspberry Pi](http://raspberrypi.org/) running Debian

* [Swagger Scalatra](https://www.scalatra.org).  I was using nodejs first but the phidget support isn't great for it (yet).

### Getting started

I was running on a Phidgets SBC but it was hitting memory issues when running control algorithms.  So I've switched
to the Raspberry Pi 512MB device.  It takes a little more work to get started (not that much though).  I'm still using
the Phidgets IO boards.

First, setting up the Raspberry Pi was pretty easy--grab an image of the OS from here:

[Soft-float Debian “wheezy”](http://www.raspberrypi.org/downloads) is required for the JDK to work properly on the device.  This
is done by following the OS-X command-line instructions here:

```
http://elinux.org/RPi_Easy_SD_Card_Setup#Copying_an_image_to_the_SD_Card_in_Mac_OS_X
```

specifically:

```
tony$ df -h
Filesystem                              Size   Used  Avail Capacity   iused    ifree %iused  Mounted on
...
/dev/disk3s1                           7.4Gi  2.1Mi  7.4Gi     1%         0        0  100%   /Volumes/NO NAME

sudo diskutil unmount /dev/disk3s1
Volume NO NAME on disk3s1 unmounted

sudo dd bs=1m if=./2013-12-20-wheezy-raspbian.img of=/dev/rdisk3
1850+0 records in
1850+0 records out
1939865600 bytes transferred in 32.088743 secs (60453150 bytes/sec)

sudo diskutil eject /dev/rdisk3
Disk /dev/rdisk3 ejected
```

After that, stick the SD card in the Pi, plug in a USB keyboard and HDMI monitor and power on.  Easy.

You'll need to install libusb before building, though!

```
sudo apt-get install libusb-dev
```

If you can't get libusb, you can follow this path:

```
wget http://sourceforge.net/projects/libusb/files/libusb-1.0/libusb-1.0.9/libusb-1.0.9.tar.bz2
tar xjf libusb-1.0.9.tar.bz2
cd libusb-1.0.9

./configure
make
sudo make install
```

The Phidget drivers then need to be installed in the OS.  You can grab them from source here:

```
wget http://www.phidgets.com/downloads/libraries/libphidget.tar.gz

```

Then unpack the libphidget.tar.gz file, cd to the directory and run the commands:

```
tar -xvf libphidget.tar.gz 
cd libphidget-*
./configure
make
sudo make install
```

Back to the software itself...

The Phidget library isn't in a maven repo, so pull it from here:

```
http://www.phidgets.com/downloads/libraries/phidget21jar.zip
```

and install it in your local maven repo like such:

```
mvn install:install-file -DgroupId=com.phidgets -DartifactId=phidget -Dversion=2.1.8 -Dpackaging=jar -Dfile=phidget21.jar
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

Make it a daemon by copying the init script into /etc/init.d then

```
update-rc.d sfth defaults
```

Might as well also update the pi.  This is the best way to do so:

https://github.com/Hexxeh/rpi-update

### Configuration

The app looks for a single JSON file called config.json in the working directory.  This
contains key-value pairs for simple configurations, as well as a mapping between zones, device IDs, and friendly names.

For example:

```json
{
  "values":{
    "saveDigitalOutputs":"true",
    "saveAnalogData":"true",
    "updateLcd":"true",
    "aggregate":"enabled",
    "dbhost":"localhost",
    "dbport":"27017",
    "dbuser":"username",
    "dbpassword":"password",
    "database":"my_database"
  },
  "inputZones": [
    {
      "logicalPosition": 0,
      "deviceId": "ABCD",
      "position": 0,
      "name": "kids room"
    },
    {
      "logicalPosition": 1,
      "deviceId": "ABCD",
      "position": 1,
      "name": "garage"
    },
    {
      "logicalPosition": 4,
      "deviceId": "EFGH",
      "position": 0,
      "name": "guest house"
    }
  ]
}
```

Note the `logicalPosition` is a unique position for inputs which is somewhat arbitrary--it is used for identification in the mongodb database.  The `deviceId` is the serial number of the phidget input board.  This is because you need more than 8 inputs, right?


### Troubleshooting
I've spent hours because of these issues.  Hope it helps.

#### The call to open() hangs forever.  What gives?
Look at this [link](http://www.lothar.com/Projects/Phidgets/) for info on configuring udev to properly allow access to the USB devices.  Also try running your program as root.

### Making it yours

This server uses the [Swagger Codegen](https://github.com/wordnik/swagger-codegen) project to generate the scalatra server.  You can 
repeat this process by running the `./bin/server.sh` command--the codegen will read from `src/main/scripts/ScalatraServerGenerator.scala`,
which in turn uses templates inside `src/main/templates/scalatra` for the code generation.  The swagger spec files located in `src/main/templates/spec-files`.

When running the `./bin/server.sh` command, the codegen will read the spec files and generate a server from them.  That means the server
itself is defined by the spec files, and the code is generated from them.  To avoid overwriting the actual implementation on every api change,
the codegen delegates the logic to a service tier, which is also defined in the templates.
