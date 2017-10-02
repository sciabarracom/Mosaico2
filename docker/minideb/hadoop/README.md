# hadoop image for HSDF only

Pick one node to be the master, give him a name and set MASTER=<name> for all the nodes

You need to start then one name node, with `IS_NAME_NODE=1`
and then at least 2 data nodes with `IS_DATA_NODE=1`
and finally one secondary name node with `IS_SECONDARY_NAME_NODE=1`
