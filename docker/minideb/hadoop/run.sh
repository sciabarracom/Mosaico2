#!/bin/bash

cat <<EOF >/usr/hadoop/etc/hadoop/core-site.xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
 <property>
   <name>fs.defaultFS</name>
   <value>hdfs://$MASTER/</value>
 </property>
 <property>
   <name>io.file.buffer.size</name>
   <value>131072</value>
 </property>
</configuration>
EOF

cat <<EOF >/usr/hadoop/etc/hadoop/hdfs-site.xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
 <property>
  <name>dfs.namenode.name.dir</name>
  <value>${DATA_DIR:-/data}</value>
 </property>
 <property>
  <name>dfs.blocksize</name>
  <value>268435456</value>
 </property>
 <property>
  <name>dfs.namenode.handler.count</name>
  <value>100</value>
 </property>
 <property>
   <name>dfs.namenode.datanode.registration.ip-hostname-check</name>
   <value>false</value>
 </property>
</configuration>
EOF

if test -n "$IS_NAME_NODE"
then
    if ! test -e "$DATA_DIR/.formatted"
    then /usr/hadoop/bin/hdfs namenode -format "cluster"
         touch "$DATA_DIR/.formatted"
    fi
    /usr/hadoop/bin/hdfs namenode
elif test -n "$IS_DATA_NODE"
then /usr/hadoop/bin/hdfs datanode
elif test -n "$IS_SECONDARY_NAME_NODE"
then exec /usr/hadoop/bin/hdfs secondarynamenode
else echo "*** No role set, sorry ***"
fi
