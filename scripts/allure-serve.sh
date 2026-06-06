#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."

if [ ! -f target/site/allure-maven-plugin/index.html ]; then
    mvn -q allure:report
fi

exec .allure/bin/allure open target/site/allure-maven-plugin
