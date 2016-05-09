#!/bin/sh
JAVA_OPTIONS=""
#JAVA_OPTIONS="-Xmx256m -Xss256k -XX:MaxMetaspaceSize=48m -XX:MaxDirectMemorySize=16m -XX:+UseCompressedOops"
JAVA_OPTIONS="$JAVA_OPTIONS -Djava.security.egd=file:/dev/./urandom"
if [ "$JAVA_DEBUG" = true ]; then
  JAVA_OPTIONS="$JAVA_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005";
fi

SPRING_OPTIONS="$SPRING_OPTIONS --info.version=${VERSION}"
SPRING_OPTIONS="$SPRING_OPTIONS --deviceId=${DEVICE_ID}"
SPRING_OPTIONS="$SPRING_OPTIONS --consulHost=${CONSUL_URL}"

echo "SPRING: ${SPRING_OPTIONS}"

exec java $JAVA_OPTIONS -jar /badge-reader.jar $SPRING_OPTIONS "$@"