#!/bin/bash
CFG=/usr/zeppelin/conf/zeppelin-env.sh

if test -n "$MASTER"
then SPARK_MASTER=spark://"$MASTER":7077
fi
#SPARK_MASTER=spark://"$(getent hosts $MASTER | awk '{print $1}')":7077

if test -n "$HADOOP"
then cat <<EOF >/usr/hadoop/etc/hadoop/core-site.xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
 <property>
   <name>fs.defaultFS</name>
   <value>hdfs://$HADOOP/</value>
 </property>
 <property>
   <name>io.file.buffer.size</name>
   <value>131072</value>
 </property>
</configuration>
EOF
fi

echo "*** zeppelin-env ***"
env \
  MASTER="${SPARK_MASTER:-local[*]}" \
  SPARK_HOME=/usr/spark \
  HADOOP_HOME=/usr/hadoop \
  PATH="$HADOOP_HOME/bin:$SPARK_HOME/bin:$PATH" \
  SPARK_DIST_CLASSPATH=$(/usr/hadoop/bin/hadoop classpath) \
 | sed -E -e 's/"/\\\"/g' -e 's/^(\w+)=/export \1="/' -e 's/$/"/' \
 | tee $CFG
chmod +x $CFG
cat $CFG
echo "*** zeppelin ***"
cd /usr/zeppelin
/usr/zeppelin/bin/zeppelin.sh
