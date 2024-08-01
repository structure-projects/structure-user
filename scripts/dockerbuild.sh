#!/bin/bash
version=$1
if [ -z "$version" ]; then
    version=1.0.1
fi
echo 'docker-build'
cd ../structure-user-boot
mvn clean package -Dmaven.test.skip=true -Drevision=$version
cd ../structure-user-cloud
mvn clean package -Dmaven.test.skip=true -Drevision=$version
cd ../
docker build -t registry.cn-hangzhou.aliyuncs.com/structured/structured-user-center:$version .



