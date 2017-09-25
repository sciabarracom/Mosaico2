#!/bin/bash
# workaround for a docker bug in aws
cd /usr/spark
set -x
# HADOOP_HOME=/usr/hadoop
# SPARK_DIST_CLASSPATH=$(/usr/hadoop/bin/hadoop classpath)
env \
  MASTER="${SPARK_MASTER:-local[*]}" \
  SPARK_HOME=/usr/spark \
  PATH="$SPARK_HOME/bin:$PATH" \
  bin/spark-shell "$@"
