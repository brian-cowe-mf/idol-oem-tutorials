set JAVA_HOME=C:\Program Files\OpenLogic\openlogic-openjdk-11.0.16+8-windows-x64

set PATH=%JAVA_HOME%\bin;%PATH%

set PROGRAM=FetchCollect
set ACI_HOST=localhost
set ACI_PORT=7010
set IDENTIFIERS_CSV=PGlkIHQ9IkZpbGUiIHM9Ik1ZVEFTSzEiIHI9ImRiaWQ6QUFETVEwZ0FSZ2ZoU0hyT0QyMzlsRlZRV3RMVUpEMGhwTkU6L2RvY3VtZW50cy9kcm9wYm94Y29ubmVjdG9yXzEyLjEzLjBfcmVsZWFzZW5vdGVzX2VuLnBkZiI+PHAgbj0iUEFUSCIgdj0iL2RvY3VtZW50cy9kcm9wYm94Y29ubmVjdG9yXzEyLjEzLjBfcmVsZWFzZW5vdGVzX2VuLnBkZiIvPjxwIG49IlVJRCIgdj0iZGJpZDpBQURNUTBnQVJnZmhTSHJPRDIzOWxGVlFXdExVSkQwaHBORSIvPjwvaWQ+
set DESTINATION_FOLDER=./collect

set ACI_API_TUTORIAL_PROGRAM_ENCRYPTION_KEY=REPLACE_WITH_MY_LONG_KEY_STRING
rem set ACI_API_TUTORIAL_PROGRAM_ENCRYPTION_KEY=

java -jar target/%PROGRAM%-1.0-SNAPSHOT-jar-with-dependencies.jar %ACI_HOST% %ACI_PORT% "%IDENTIFIERS_CSV%" %DESTINATION_FOLDER%