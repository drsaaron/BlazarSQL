#! /bin/ksh

# run the docker container, based on https://fredrikaverpil.github.io/2016/07/31/docker-for-mac-and-gui-applications/
XSOCK=/tmp/.X11-unix
ip=$(ifconfig en0 | grep inet | awk '$1=="inet" {print $2}')
xhost + $ip

# find the crypto file.
cryptoFile=$(grep blazartech.crypto.file ~/.blazartech/crypto.properties | awk -F= '{ print $2 }')
cryptoFilePath=/root #$(dirname $cryptoFile)

# the preferences will be stored in /root/.java (as seen by the container) so mount a real directory
# to that location.  
prefDir=~/.java
[ -d $prefDir ] || mkdir $prefDir

# run the container
docker run -it -v $XSOCK:$XSOCK -e DISPLAY=$ip:0 -v $HOME/BlazarSQL.profiles:/root/BlazarSQL.profiles -v $HOME/.blazartech:/root/.blazartech -v $HOME/.blazartech/crypto:$cryptoFilePath -v $prefDir:/root/.java blazarsql


