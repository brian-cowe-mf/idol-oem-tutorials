#!/bin/sh

ACI_API_HOME=/opt/MicroFocus/ACI_API/IDOLJavaSDK_12.10.0
#JAVA_HOME=/opt/Java/jdk1.8.0_321

#PATH=$MAVEN_HOME/bin:$JAVA_HOME/bin:$PATH

PROGRAM=GetStatus
ACI_HOST=localhost
ACI_PORT=7000

ACI_API_TUTORIAL_PROGRAM_ENCRYPTION_KEY=REPLACE_WITH_MY_LONG_KEY_STRING
export ACI_API_TUTORIAL_PROGRAM_ENCRYPTION_KEY

java -jar target/$PROGRAM-1.0-SNAPSHOT-jar-with-dependencies.jar $ACI_HOST $ACI_PORT