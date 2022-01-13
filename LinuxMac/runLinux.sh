#!/bin/bash

export CLASSPATH=$CLASSPATH:../lib/jdom-2.0.6.jar
export CLASSPATH=$CLASSPATH:../lib/jna-5.10.0.jar
export CLASSPATH=$CLASSPATH:../lib/jna-platform-5.10.0.jar

javac @compile.list

cd bin

java src.Controleur

sleep 99
