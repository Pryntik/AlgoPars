#!/bin/bash

export CLASSPATH=$CLASSPATH:../lib/jdom-2.0.6.jar

javac @compile.list

cd bin

java src.Controleur