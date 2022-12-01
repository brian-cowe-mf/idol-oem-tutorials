#!/bin/sh

ACI_API_HOME=/opt/MicroFocus/ACI_API/IDOLJavaSDK_12.10.0
#JAVA_HOME=/opt/Java/jdk1.8.0_321

#PATH=$MAVEN_HOME/bin:$JAVA_HOME/bin:$PATH

PROGRAM=EduceRedact
ACI_HOST=localhost
ACI_PORT=13000
set MODE=e
set ENTITIES_CSV=REPLACE_WITH_ENTITIES
rem set ENTITIES_CSV=number/cc
set TEXT=REPLACE_WITH_TEXT
rem set TEXT=378282246310005

ACI_API_TUTORIAL_PROGRAM_ENCRYPTION_KEY=REPLACE_WITH_MY_LONG_KEY_STRING
export ACI_API_TUTORIAL_PROGRAM_ENCRYPTION_KEY

java -jar target/$PROGRAM-1.0-SNAPSHOT-jar-with-dependencies.jar $MODE $ACI_HOST $ACI_PORT "$ENTITIES_CSV" "$TEXT"