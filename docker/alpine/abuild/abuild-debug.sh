#!/bin/bash
echo "Test: bash /home/build.sh package package.apk"
docker run --entrypoint=/bin/sh -ti \
-v $PWD/abuild:/home/abuild \
-v $PWD/target:/home/packager/packages \
abuild:0.4-1
