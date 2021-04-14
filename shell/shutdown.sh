#!/bin/bash

source env.sh

count=$(ps -ef | grep $PROCESS | grep -v grep | grep -v tail | wc -l)
if [ $count -eq 1 ]
 then
    p_string=$(ps -ef | grep $PROCESS | grep -v grep | grep -v tail)
    pid=$(echo $p_string | awk '{print $2}')
    echo "kill pid : $pid"
    kill $pid
else
    echo "$PROCESS is not running"
fi
