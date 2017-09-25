#!/bin/bash
## configure env for spark
CFG=/usr/spark/conf/spark-env.sh
echo "*** spark-env ***"
if test -z "$MASTER"
then MASTER_IP="$(hostname -i)"
else MASTER_IP="$(getent hosts $MASTER | awk '{print $1}')"
fi
#
# SPARK_DIST_CLASSPATH="$(/usr/hadoop/bin/hadoop classpath)"
env \
 SPARK_NO_DAEMONIZE=1 \
 SPARK_HOME=/usr/spark \
 HADOOP_HOME=/usr/spark \
 SPARK_MASTER=spark://$MASTER:7077 \
 SPARK_MASTER_IP=$MASTER_IP \
 SPARK_MASTER_HOST=$MASTER \
 PATH="/usr/spark/bin:$PATH" \
 | sed -E -e 's/"/\\\"/g' -e 's/^(\w+)=/export \1="/' -e 's/$/"/' \
 | grep -v spark://:7077 \
 | tee $CFG
cat $CFG
chmod +x $CFG
echo "*** spark ***"
cd /usr/spark
mkdir logs
if test -z "$MASTER"
then exec sbin/start-master.sh
else exec sbin/start-slave.sh "spark://$MASTER:7077"
fi
