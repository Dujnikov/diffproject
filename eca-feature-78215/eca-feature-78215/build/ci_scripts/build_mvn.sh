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
cd "${CURRENT_DIR}/src/aeca-EEE/"
mvn install
cd "${CURRENT_DIR}/src/aeca-EEE/target/"
zip -r aeca-EEE-0.1_${PACKAGE_FULLNAME}.zip *jar
cd "${CURRENT_DIR}/src/aeca-EEE/"
mvn javadoc:javadoc
mvn dependency:tree > docs/maven_dependency.txt
