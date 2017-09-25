# Spark image

- runs standalone spark as masters or as slave according the MASTER environment variable
- if MASTER is set, it must be the name of the MASTER and the image is started as a worker
- otherwise the image is started as a masters
- all the environment variables are included in spark-env.sh
