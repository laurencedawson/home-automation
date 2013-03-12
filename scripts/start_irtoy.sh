sudo mkdir /var/run/lirc
sudo echo  '0' > /var/run/lirc/lircd.pid
sudo lircd --device=/dev/ir-toy --driver=usb_irtoy
#sudo lircd --device=/dev/ttyACM0 --driver=usb_irtoy
