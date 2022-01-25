#!/bin/bash
# TODO: данный скрипт должен осуществлять сборку всех компонентов и подсистем решения

#params
###################################################################################################
if [ -z "${NAME}" ]; then
    NAME="aeca"
fi

if [ -z "${MAJOR}" ]; then
    MAJOR="0"
fi

if [ -z "${MINOR}" ]; then
    MINOR="0"
fi

if [ -z "${BUILD}" ]; then
    BUILD="0"
fi

if [ -z "${VERSION}" ]; then
    VERSION="${MAJOR}.${MINOR}.${BUILD}"	
fi

if [ -z "$PACKAGE_FULLNAME" ]; then
    PACKAGE_FULLNAME="${NAME}_${VERSION}"
fi

echo "Testing distribution of ${PACKAGE_FULLNAME}"
CURRENT_DIR=$(pwd)
echo "Working at ${CURRENT_DIR}"
cd "${CURRENT_DIR}/src/"
find . -maxdepth 2 -type f -name "pom.xml" -execdir sh -c 'mvn test' {} \;
