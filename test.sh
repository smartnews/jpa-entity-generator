#!/bin/bash -x

./gradlew clean test jar && \
cd sample && \
./gradlew entityGen && \
cp -pr db testdb && \
#git diff --exit-code && \
./gradlew test
