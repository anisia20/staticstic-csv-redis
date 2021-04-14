#!/bin/bash
source env.sh

cd $AGENT_HOME
$JAVA -jar -Dspring.profiles.active=encdes $AGENT_HOME/lib/agent.war $1 $2
