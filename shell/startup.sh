#!/bin/bash

source env.sh

cd $AGENT_HOME
count=$(ps -ef | grep $PROCESS | grep -v grep | grep -v tail | wc -l)
if [ $count -eq 0 ]
then
    $JAVA -jar -Dspring.profiles.active=$PROCESS -Dspring.config.location=classpath:/application.yml,$AGENT_HOME/conf/agent.yml -Dmybaits.mapper=$AGENT_HOME/conf/$MAPPER/**.xml $AGENT_HOME/lib/agent.war > /dev/null 2>&1 &
    ps -ef | grep $PROCESS
else
    echo "$PROCESS is already running."
fi
