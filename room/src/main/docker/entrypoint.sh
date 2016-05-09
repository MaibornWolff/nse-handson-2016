#!/bin/sh
JAVA_OPTIONS=""
#JAVA_OPTIONS="-Xmx256m -Xss256k -XX:MaxMetaspaceSize=48m -XX:MaxDirectMemorySize=16m -XX:+UseCompressedOops"
JAVA_OPTIONS="$JAVA_OPTIONS -Djava.security.egd=file:/dev/./urandom"
if [ "$JAVA_DEBUG" = true ]; then
  JAVA_OPTIONS="$JAVA_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005";
fi
SPRING_OPTIONS="$SPRING_OPTIONS --spring.rabbitmq.host=handson-server"
SPRING_OPTIONS="$SPRING_OPTIONS --spring.rabbitmq.port=5672"
SPRING_OPTIONS="$SPRING_OPTIONS --spring.data.mongodb.host=handson-server"
SPRING_OPTIONS="$SPRING_OPTIONS --spring.data.mongodb.port=27017"

SPRING_OPTIONS="$SPRING_OPTIONS --info.version=${VERSION}"

exec java $JAVA_OPTIONS -jar /room.jar $SPRING_OPTIONS "$@"