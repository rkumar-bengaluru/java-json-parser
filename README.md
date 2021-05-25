# java-json-parser
This parser is primaryly written to generate formated Json and Html file from Json. Please see
the demo link below to make sure this is what you are looking for.

`<Demo>` : <https://rkumar-bengaluru.github.io/java-json-parser/>
## Formatted JSON Html File Generator
A fast and minimal JSON parser and writer for Java. It's not an object mapper, but a bare-bones library that aims at being
* **minimal**: no dependencies, single package with just a few classes, small download size 
* **lightweight**: object representation with minimal memory footprint 

Java-Json-Parser is fully covered by unit tests.

The class `RJsonParser` is the entrypoint to the java-json-parser API, use it to parse and to create JSON.
### Parse JSON

You can parse JSON from a `String` or from a `java.io.Reader`. 

```java
String json = "{\"name\":\"sonoo\",\"salary\":600000.0,\"age\":27}";
RJsonParser instance = new RJsonParser(string);
RJsonObject json = instance.parse();
```
### JSON Formatted Html
```java
String json = "{\"name\":\"sonoo\",\"salary\":600000.0,\"age\":27}";
RJsonParser instance = new RJsonParser(json);
StringBuilder builder = new StringBuilder();
instance.parse().toHtml(builder,-1);
```
Build
-----
To build java-json-parser on your machine, checkout the repository, `cd` into it, and call:
```
gradlew build
```
## License

MIT
**Free Software, Hell Yeah!**
