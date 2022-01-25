#!/bin/bash
# TODO: данный скрипт должен ообновление решения на стендах

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

echo "Updating..."