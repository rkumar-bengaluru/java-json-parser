@ECHO OFF
set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;C:\\rupak\\2021\\java-json-parser\\build\\classes\\java\\main;lib/*;.;

echo %CLASSPATH%s

java -Dlog4j2.configurationFile=C:\\rupak\\2021\\java-json-parser\\log4j2.xml org.rk.json.parser.RJsonLexerMain

