#!/usr/bin/perl -w

# http://pastebin.com/eksBABuD
# Reads data from Current Cost and e.on energy monitor devices via serial port.

use strict;
use Device::SerialPort qw( :PARAM :STAT 0.07 );

my $PORT = "/dev/ttyUSB0";

my $ob = Device::SerialPort->new($PORT);
$ob->baudrate(57600);
$ob->write_settings;

open(SERIAL, "+>$PORT");
while (my $line = <SERIAL>) {
    if ($line =~ m!<tmpr> *([\-\d.]+)</tmpr>.*<watts>0*(\d+)</watts>!) {
        my $temp = $1;
        my $watts = $2;
        # print "{\n\t\"stats\": {\n\t\t\"watts\": $watts,\n\t\t\"temp\": $temp\n\t}\n}\n";
        open (MYFILE, '>stats.json');
        print MYFILE "{\n\t\"stats\": {\n\t\t\"watts\": $watts,\n\t\t\"temp\": $temp\n\t}\n}\n";
        close (MYFILE); 
    }
}
