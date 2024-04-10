#!/bin/bash

set -eo pipefail

source /scripts/envvars.sh /scripts/env.yml

echo "Environment Variables for the application"

if [ "$ENVIRONMENT" = "dev" ] || [ "$ENVIRONMENT" = "production" ]; then
  exec \
      java \
      -XX:MinRAMPercentage:50.0 \
      -XX:MaxRAMPercentage:80.0 \
      -XshowSettings:vm \
      -XX:+HeapDumpOnOutOfMemoryError \
      -DCom.sun.management.jmxremote \
      -DCom.sun.management.jmxremote.port=7000 \
      -DCom.sun.management.jmxremote.local.only=false \
      -DCom.sun.management.jmxremote.authenticate=false \
      -DCom.sun.management.jmxremote.ssl=false \
      -XX:+UseG1GC \
      -XX:+UseNUMA \
      -XX:MaxGCPauseMillis=100 \
      -XX:ParallelGCThreads=20 \
      -XX:ConcGCThreads=5 \
      -Xlog:gc \
      -jar \
      -DSpring.profile.active="$ENVIRONMENT" \
      -DFile.encoding=UTF-8 \
      /app/build/app.jar
  else
    exec \
    java \
      -XX:MinRAMPercentage:50.0 \
      -XX:MaxRAMPercentage:80.0 \
      -XshowSettings:vm \
      -XX:+HeapDumpOnOutOfMemoryError \
      -DCom.sun.management.jmxremote \
      -DCom.sun.management.jmxremote.port=7000 \
      -DCom.sun.management.jmxremote.local.only=false \
      -DCom.sun.management.jmxremote.authenticate=false \
      -DCom.sun.management.jmxremote.ssl=false \
      -XX:+UseG1GC \
      -XX:+UseNUMA \
      -XX:MaxGCPauseMillis=100 \
      -XX:ParallelGCThreads=20 \
      -XX:ConcGCThreads=5 \
      -Xlog:gc \
      -jar \
      -DSpring.profile.active="$ENVIRONMENT" \
      -DFile.encoding=UTF-8 \
      /app/build/app.jar
fi
