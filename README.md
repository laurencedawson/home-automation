# home-automation

![image](http://i.imgur.com/zOqRQApm.png)

#### Overview
home-automation is a simple attempt to utilise a Raspberry Pi for various home automation tasks.

Currently the project is pretty basic and uses [LIRC](http://www.lirc.org/) to control a TV, however more functionality such power socket control is being added.

#### Details
The project uses a Raspberry Pi to handle all requests. It's running Apache 2 and through the script remote.sh, allows a user to specify a remote control command that in turn fired by the IR Toy.There is an accompanying Android app optimised for a Nexus 7, which currently allows a user to control their TV through the app.

#### Hardware Used
* [Raspberry Pi](http://www.raspberrypi.org/) (using Raspbian)
* [USB IR Toy v2](http://dangerousprototypes.com/docs/USB_Infrared_Toy)
* [TellStick](http://www.telldus.se/products/tellstick)
* RF compatible remote sockets
* Sony RM-ED035 TV
* Nexus 7 (or any Android device)

#### License

home-automation is distributed under the [GNU General Public License v3](http://www.gnu.org/licenses) - see the accompanying LICENSE file for more details.


#### Extra

* [Details on how to setup the IR Toy for use with LIRC in Linux](http://dangerousprototypes.com/forum/viewtopic.php?f=29&t=4747)
* [Dated IR Toy installation instructions](http://dangerousprototypes.com/docs/USB_IR_Toy:_Configure_LIRC#Any_Recent_Linux_Distro)