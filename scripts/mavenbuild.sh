#!/bin/bash
version=$1
if [ -z "$version" ]; then
    version=1.0.2
fi
echo 'maven-build'
cd ../structure-user-dependencies
mvn clean package install -Dmaven.test.skip=true -Drevision=$version
cd ../structure-user-boot
mvn clean package -Dmaven.test.skip=true -Drevision=$version
cd ../structure-user-cloud
mvn clean package -Dmaven.test.skip=true -Drevision=$version