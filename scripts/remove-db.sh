#!/bin/bash
set -e

docker-compose down
docker volume rm irclojure_dbdata
docker-compose up -d