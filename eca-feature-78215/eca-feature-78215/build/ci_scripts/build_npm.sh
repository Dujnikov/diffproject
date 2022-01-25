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

echo "Building distribution of ${PACKAGE_FULLNAME}"

CURRENT_DIR=$(pwd)
echo "Working at ${CURRENT_DIR}"
cd "${CURRENT_DIR}/src/aeca-WebClient/"
npm install
npm run build
cd "${CURRENT_DIR}/src/aeca-WebClient/public/"
cp .htaccess "${CURRENT_DIR}/src/aeca-WebClient/build/"
cd "${CURRENT_DIR}/src/aeca-WebClient/build/"
zip -r build_npm_${PACKAGE_FULLNAME}.zip .

ARTIFACTS_DIR="${CURRENT_DIR}/artifacts"
if [ -d "${ARTIFACTS_DIR}" ]; then
    rm -rf "${ARTIFACTS_DIR}/"
    echo "old ${ARTIFACTS_DIR} successfully removed"
fi
BUILD_DIR="${CURRENT_DIR}/src/aeca-WebClient/build/"

if ! [ -d "${ARTIFACTS_DIR}" ]; then
    mkdir --verbose ${ARTIFACTS_DIR}    
fi

find ${BUILD_DIR} -name "*${PACKAGE_FULLNAME}*.zip" -exec cp {} ${ARTIFACTS_DIR}/ \;
find ${BUILD_DIR} -name "*.zip*" -exec cp {} ${ARTIFACTS_DIR}/ \;

cd "${CURRENT_DIR}/src/aeca-WebClient/"
rm -r node_modules
