# epever-solar-modbus

## Synopsis

Library (java jar) supporting communication with EPever solar charge controllers over USB with Modbus protocol.

It uses [jlibmodbus](https://github.com/kochedykov/jlibmodbus) to send epever specific messages.

### Note: Project has not yet implemented read coils or write registers.


## Motivation

Need a pure Java approach to managing EPever charge controllers.  The intent is to have
a Java web application and misc utilities that do not depend on scripts, gotracer (go), or epsolar-tracer (python).

## Installation

Maven and Java JDK 1.8+ are required.  Example build:

Build jar from project folder:
```
# build jar and make available in local repo for other projects
mvn clean install

```
