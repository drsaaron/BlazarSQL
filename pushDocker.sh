#! /bin/sh

imageName=drsaaron/blazarsql
version=$(getPomAttribute.sh version)
imageVersion=$(echo $version | sed 's/-RELEASE//')

docker push $imageName:$imageVersion
