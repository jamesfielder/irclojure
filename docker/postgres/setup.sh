#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER irc WITH PASSWORD 'ircpass';
    CREATE DATABASE ircdb;
    GRANT ALL PRIVILEGES ON DATABASE ircdb TO irc;
EOSQL
