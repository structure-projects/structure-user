#!/bin/bash
version=$1
if [ -z "$version" ]; then
    version=1.0.2
fi
echo 'maven-build'
mvn clean package -f ../structure-user-boot/pom.xml -Dmaven.test.skip=true -Drevision=$version
mvn clean package -f ../structure-user-cloud/pom.xml -Dmaven.test.skip=true -Drevision=$version
