#!/usr/bin/env bash
#发行新的版本上传到中心仓库可以执行这个脚本
#!/bin/bash
version=$1
if [ -z "$version" ]; then
    version=1.0.2
fi
cd ../
cd structure-user-dependencies
mvn clean deploy -P release,oss -Dmaven.test.skip=true  -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Drevision=$version
