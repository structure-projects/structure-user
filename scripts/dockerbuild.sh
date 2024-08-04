#!/bin/bash
version=$1
if [ -z "$version" ]; then
    version=1.0.2
fi
echo 'docker-build'
cd ..
sudo docker build -t registry.cn-hangzhou.aliyuncs.com/structured/structured-user-center:$version .
sudo docker push registry.cn-hangzhou.aliyuncs.com/structured/structured-user-center:$version





