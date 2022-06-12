#!/bin/bash

if [ $1 = "dev" ]; then
    mvn clean package -DskipTests --settings .build/settings.xml
else
    mvn clean package -DskipTests
fi