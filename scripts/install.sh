#!/usr/bin/env bash
#在本地仓库安装.RELEASE
version=$1
if [ -z "$version" ]; then
    version=1.0.2
fi
mvn clean install -f ../structure-user-dependencies/pom.xml -Dmaven.test.skip=true -Drevision=$version
