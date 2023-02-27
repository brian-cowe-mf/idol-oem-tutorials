set JAVA_HOME=C:\Program Files\OpenLogic\openlogic-openjdk-11.0.16+8-windows-x64

set PATH=%JAVA_HOME%\bin;%PATH%

set PROGRAM=MSProcess
set ACI_HOST=localhost
set ACI_PORT=14000
set CONFIG_NAME=REPLACE_WITH_CONFIG_NAME
set SOURCE_FILE_PATH=REPLACE_WITH_SOURCE_FILE_PATH

set ACI_API_TUTORIAL_PROGRAM_ENCRYPTION_KEY=REPLACE_WITH_MY_LONG_KEY_STRING
rem set ACI_API_TUTORIAL_PROGRAM_ENCRYPTION_KEY=

java -jar target/%PROGRAM%-1.0-SNAPSHOT-jar-with-dependencies.jar %ACI_HOST% %ACI_PORT% %CONFIG_NAME% %SOURCE_FILE_PATH%