#!/bin/bash
# http://computing.dcu.ie/~humphrys/Notes/CGI/index.html

echo "Content-type: text/html"
argument=`echo "$QUERY_STRING" | sed "s|q=||"`
echo $(tdtool --on $QUERY_STRING);
