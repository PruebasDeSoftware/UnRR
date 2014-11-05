#!/bin/bash

# Clean up
rm -f *.class *.exe *.jar

# Build Translate.jar
javac Translate.java
jar cvfe translate.jar Translate Translate.class

# Build ARM replay.exe
arm-linux-gnueabihf-gcc-4.8 -static -o replay.exe replay.c

