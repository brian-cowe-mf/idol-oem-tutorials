#!/bin/sh

#JAVA_HOME=/opt/Java/jdk1.8.0_321

#PATH=$MAVEN_HOME/bin:$JAVA_HOME/bin:$PATH

PROGRAM=MSProcess
ACI_HOST=localhost
ACI_PORT=14000
CONFIG_NAME=REPLACE_WITH_CONFIG_NAME
SOURCE_FILE_PATH=REPLACE_WITH_SOURCE_FILE_PATH

ACI_API_TUTORIAL_PROGRAM_ENCRYPTION_KEY=REPLACE_WITH_MY_LONG_KEY_STRING
export ACI_API_TUTORIAL_PROGRAM_ENCRYPTION_KEY

java -jar target/$PROGRAM-1.0-SNAPSHOT-jar-with-dependencies.jar $ACI_HOST $ACI_PORT $CONFIG_NAME $SOURCE_FILE_PATH
