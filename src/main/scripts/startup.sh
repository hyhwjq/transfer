#!/bin/sh
for i in `ls ./lib/*.jar`
do
   CLASSPATH=${CLASSPATH}:${i}
done

export CLASSPATH=${CLASSPATH}:conf
export LD_LIBRARY_PATH=./:${LD_LIBRARY_PATH}

java -cp ".:${CLASSPATH}" com.yjcloud.transfer.ServiceProvider