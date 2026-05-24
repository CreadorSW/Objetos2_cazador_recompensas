#!/usr/bin/env bash
set -e
xdg-open http://localhost:8080/ &>/dev/null &
mvn jetty:run
