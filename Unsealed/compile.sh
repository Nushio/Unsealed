#!/bin/bash
rm -rf bin/ && mkdir bin && javac src/net/k3rnel/unsealed/DesktopLauncher.java -sourcepath src -d bin -classpath :libs/* && cp -R assets/* bin/ && ant
