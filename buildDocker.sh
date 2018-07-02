#! /bin/ksh

mvn clean install
docker build -t blazarsql .
