#!/bin/bash

# change to directory containing JAR
cd /usr/local/bin

# start java app:
java -Denv=PROD -jar PmailDaemonRunner-1.0-SNAPSHOT.jar "0 13 * * 5 ?"

# start all 10 minutes
#java -Denv=PROD -jar PmailDaemonRunner-1.0-SNAPSHOT.jar "0 0/5 * * * ? *"