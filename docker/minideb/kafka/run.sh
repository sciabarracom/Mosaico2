#!/bin/bash
cd /usr/kafka
mkdir -p /data/logs 2>/dev/null
sed \
  -e "s!zookeeper\.connect=.*!zookeeper.connect=$ZOOKEEPER:2181!" \
  -e "s!#listeners=.*!listeners=$LISTENERS!" \
  -e "s!#advertised\.listeners=.*!advertised.listeners=${ADVERTISED_LISTENERS:-$LISTENERS}!" \
  -e "s!log\.dirs=/tmp/kafka-logs!log.dirs=/data/kafka/logs!" \
  -e '$aauto.create.topics.enable='"${AUTO_CREATE_TOPIC:-true}" \
  -e '$ainitial.rebalance.delay.ms=0' \
  </usr/kafka/config/server.properties \
  >/usr/kafka/config/kafka.properties
if test -n "$IS_ZOOKEEPER"
then echo "*** ZOOKEEPER ***"
     /usr/kafka/bin/zookeeper-server-start.sh /usr/kafka/config/zookeeper.properties
elif test -n "$IS_KAFKA"
then echo "*** KAFKA ***"
     /usr/kafka/bin/kafka-server-start.sh /usr/kafka/config/kafka.properties $KAFKA_ARGS
else echo "*** No role defined ***"
fi
