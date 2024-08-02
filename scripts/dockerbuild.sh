#!/bin/bash
version=$1
if [ -z "$version" ]; then
    version=1.0.2
fi
echo 'docker-build'
mvn clean package -f ../structure-user-boot/pom.xml -Dmaven.test.skip=true -Drevision=$version
mvn clean package -f ../structure-user-cloud/pom.xml -Dmaven.test.skip=true -Drevision=$version
cd ..
sudo docker build -t registry.cn-hangzhou.aliyuncs.com/structured/structured-user-center:$version .



