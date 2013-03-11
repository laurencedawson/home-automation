#!/bin/bash
# http://computing.dcu.ie/~humphrys/Notes/CGI/index.html

echo "Content-type: text/html"
argument=`echo "$QUERY_STRING" | sed "s|q=||"`
echo $(irsend SEND_ONCE Sony_RM-ED035 $QUERY_STRING $QUERY_STRING $QUERY_STRING);
