#!/bin/sh

ACI_API_HOME=../../../../../../IDOLCSDK_12.4.0_LINUX_X86_64
ACI_API_LIB=$ACI_API_HOME

LD_LIBRARY_PATH=$ACI_API_HOME
export LD_LIBRARY_PATH

# Replace the values for the following parameters for your environment
ACI_HOSTNAME=localhost
ACI_PORT=13000
MODE=e
ENTITIES_CSV=REPLACE_WITH_ENTITIES
#ENTITIES_CSV=number/cc
TEXT=REPLACE_WITH_TEXT
#TEXT=378282246310005

ACI_API_TUTORIAL_PROGRAM_ENCRYPTION_KEY=REPLACE_WITH_MY_LONG_KEY_STRING
export ACI_API_TUTORIAL_PROGRAM_ENCRYPTION_KEY

./educe_redact.exe $MODE $ACI_HOSTNAME $ACI_PORT "$ENTITIES_CSV" "$TEXT"
