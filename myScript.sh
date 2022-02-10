#!/bin/bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
cd /Zhenya2009/crm
git pull
mvn clean install
nohup java -jar /Zhenya2009/target/crm-0.0.1-SNAPSHOT.jar