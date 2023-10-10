#!/bin/bash
set +eo pipefail

timeout=180
count=0
state=""

docker compose version
docker compose -f docker/docker-compose.yml up -d db-test

docker compose -f docker/docker-compose.yml ps --format json

while [ "$state" != '"running"' ]; do
  state=$(docker compose -f docker/docker-compose.yml ps --format json | jq '.[] | select(.Service == "db-test") | .State')
  sleep 1
  count=$((count+1))
  if [ $count -gt $timeout ]; then
    echo "Timeout waiting for db-test to start"
    exit 1
  fi
done

./gradlew integrationTest
