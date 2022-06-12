#!/usr/bin/env bash

# To silent pushd output
pushd () {
    command pushd "$@" > /dev/null
}

SCRIPT_DIR="$( cd -- "$( dirname -- "${BASH_SOURCE[0]:-$0}"; )" &> /dev/null && pwd 2> /dev/null; )";

pushd $SCRIPT_DIR
pushd ../
rm -rf .build/{build,maven-build}.sh
mkdir .build
# cp ../devops/build-scripts/*.sh .build/
cp -f ../devops/build-scripts/{build,maven-build}.sh .build/
pushd .build

