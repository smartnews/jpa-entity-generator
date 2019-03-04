#!/bin/bash -x

export DB_PASSWORD=pass

./gradlew clean test jar && \
cd sample && \
./gradlew entityGen && \
git add . && \
git diff --staged --exit-code && \
cp -pr db testdb && \
./gradlew test
