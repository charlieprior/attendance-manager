#!/bin/bash
mkdir coverage
chmod 777 coverage
docker run --rm -v `pwd`/coverage:/coverage-out  citest scripts/test.sh
