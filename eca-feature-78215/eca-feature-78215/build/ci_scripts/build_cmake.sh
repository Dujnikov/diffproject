#!/bin/bash
# build для Maven

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

echo "Building distribution of ${PACKAGE_FULLNAME}"

CURRENT_DIR=$(pwd)
echo "Working at ${CURRENT_DIR}"
cd "${CURRENT_DIR}/src/aeca-COE/src/main/opensslfasad/"
cmake .
cmake --build .
zip -r libopensslfasad_${PACKAGE_FULLNAME}.zip *.so

