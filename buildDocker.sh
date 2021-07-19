#! /bin/sh

#mvn clean install

imageName=drsaaron/blazarsql
version=$(getPomAttribute.sh version)
imageVersion=$(echo $version | sed 's/-RELEASE//')

docker build -t $imageName:$imageVersion --build-arg VERSION=$version .
docker tag $imageName:$imageVersion $imageName:latest
