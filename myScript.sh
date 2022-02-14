#!/bin/bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
cd /Zhenya2009/crm
git pull
mvn clean install
cd /Zhenya2009/crm/target
nohup java -jar crm-0.0.1-SNAPSHOT.jar &