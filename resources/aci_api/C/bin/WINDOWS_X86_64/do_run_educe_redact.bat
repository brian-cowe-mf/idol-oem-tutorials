set ACI_API_HOME=..\..\..\..\..\..\..\IDOLCSDK_12.4.0_WINDOWS_X86_64
set PATH=%ACI_API_HOME%;%PATH%

rem Replace the values for the following parameters for your environment
set ACI_HOST=localhost
set ACI_PORT=13000
set MODE=e
set ENTITIES_CSV=REPLACE_WITH_ENTITIES
rem set ENTITIES_CSV=number/cc
set TEXT=REPLACE_WITH_TEXT
rem set TEXT=378282246310005

set ACI_API_TUTORIAL_PROGRAM_ENCRYPTION_KEY=REPLACE_WITH_MY_LONG_KEY_STRING
rem set ACI_API_TUTORIAL_PROGRAM_ENCRYPTION_KEY=

educe_redact.exe %MODE% %ACI_HOST% %ACI_PORT% "%ENTITIES_CSV%" "%TEXT%"