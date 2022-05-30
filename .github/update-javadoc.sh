#!/usr/bin/env bash

mvn javadoc:javadoc
rm -r docs
cp -R target/site/apidocs docs
open docs/index.html