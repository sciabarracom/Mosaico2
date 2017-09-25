# abuild: build image for alpine

this is a build image for building alpine packages.

You generally place an abuild script in an `abuild` directory
and get result in the `target` directory

Run like this:

```
docker run  -ti \
-v $PWD/abuild:/home/abuild \
-v $PWD/target:/home/packager/packages \
abuild:0.4-1 <source> <output>
```

where `<source>` is your alpine build script under the `abuild`
directory while  `<output>` is the destination file.

The `abuild-debug.sh` script helps debugging launching the image in a shell.
