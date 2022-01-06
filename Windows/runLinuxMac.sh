#!/bin/bash

javac @compile.list

cd bin
stty rows 30 cols 170
java Controleur

sleep 1

exit