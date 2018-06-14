#!/bin/bash
source ./version
./gradlew clean build --refresh-dependencies -i -Pversion=$version
