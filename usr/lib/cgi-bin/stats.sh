#!/bin/bash
# http://computing.dcu.ie/~humphrys/Notes/CGI/index.html

echo "Content-type: application/json"
echo ""
echo $(cat /home/pi/scripts/stats.json)
