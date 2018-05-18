# epever-solar-modbus

## Synopsis

Library (java jar) supporting communication with EPever solar charge controllers over USB with Modbus protocol.

It uses [jlibmodbus](https://github.com/kochedykov/jlibmodbus) to send epever specific messages.

## Motivation

Needed a pure Java approach to managing EPever charge controllers.  It supports 
a Java web application.

## Installation

Maven and Java JDK 1.8+ are required.  Example build:

Build jar from project folder:
```
# build jar and make available in local repo for other projects
mvn clean install

```
