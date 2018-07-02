#! /bin/sh

cd /app
export CLASSPATH=$(echo *.jar lib/*.jar | sed 's/ /:/g')
java com.blazartech.products.blazarsql.Main

