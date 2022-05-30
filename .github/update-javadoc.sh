#!/usr/bin/env bash

jenv shell 18
mvn javadoc:javadoc
jenv shell 1.8
rm -r docs
cp -R target/site/apidocs docs
open docs/index.html