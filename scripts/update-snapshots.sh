#!/usr/bin/env bash
# 更新快照版本
version=$1
if [ -z "$version" ]; then
    version=1.0.2-SNAPSHOT
fi
mvn clean deploy -P release,oss -f ../structure-user-dependencies/pom.xml -Dmaven.test.skip=true -Drevision=$version
