#! /bin/ksh

rm -rf target/dependency
mvn dependency:copy-dependencies
rm -f target/dependency/log4j-slf4j-impl-2.10.0.jar
