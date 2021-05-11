@ECHO OFF
set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;bin;lib/*;



javac -d bin src/org/rk/json/parser/*.java

javac -d bin test/org/rk/json/parser/*.java

javac -d bin ref/*.java

java org.rk.json.parser.Test

