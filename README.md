java-json-parser
============

A fast and minimal JSON parser and writer for Java.
It's not an object mapper, but a bare-bones library that aims at being

* **minimal**: no dependencies, single package with just a few classes, small download size 
* **lightweight**: object representation with minimal memory footprint 

Java-Json-Parser is fully covered by unit tests.

Usage
-----

The class `RJsonParser` is the entrypoint to the java-json-parser API, use it to parse and to create JSON.

### Parse JSON

You can parse JSON from a `String` or from a `java.io.Reader`. 

```java
RJsonParser instance = new RJsonParser(string);
RJsonObject json = instance.parse();
```

### JSON arrays

```java
RJsonParser instance = new RJsonParser(string);
RJsonList json = instance.parse();
```

Build
-----

To build java-json-json on your machine, checkout the repository, `cd` into it, and call:
```
gradlew build
```
