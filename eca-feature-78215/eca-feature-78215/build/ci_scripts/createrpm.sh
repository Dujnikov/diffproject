#!/bin/bash
# TODO: данный скрипт должен осуществлять сборку всех компонентов и подсистем решения в .rpm-пакет

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

#auxiliary
###################################################################################################
if [ -f "/etc/redos-release" ]; then
    OS_VERSION=`cat /etc/redos-release | sed -n 's/.*MUROM.\?(\([0-9 \.]*\)).*/\1/p'`

    if [ "$OS_VERSION" == "7.3" ]; then
	    OS_RES_DIR_NAME="RedOS7.3"
    fi

	OS_TYPE="REDOS"
	OS_SHORT_NAME="ro"
else
    echo "[ERROR] Can't define current platform!"
    exit 1
fi

#infrastructure for building
###################################################################################################
CURRENT_DIR=$(pwd)
echo "Working at ${CURRENT_DIR}"
if [ -z "$WORKSPACE" ]; then
    BUILD_DIR="$(realpath ~)/rpmbuild"
    echo "BUILD_DIR=${BUILD_DIR}"
else
    BUILD_DIR="${WORKSPACE}/rpmbuild"
fi

if [ -d "${BUILD_DIR}" ]; then
    rm -rf "${BUILD_DIR}"
    echo "old ${BUILD_DIR} successfully removed"
fi

BUILD_DIR_SOURCES="${BUILD_DIR}/SOURCES"
BUILD_DIR_SPECS="${BUILD_DIR}/SPECS"
BUILD_DIR_TMP_PACKAGE="${BUILD_DIR}/aeca"

mkdir -p --verbose ${BUILD_DIR} || exit 1
mkdir -p --verbose "${BUILD_DIR}/"{BUILD,RPMS,SOURCES,SPECS,SRPMS} || exit 1
mkdir -p --verbose ${BUILD_DIR_TMP_PACKAGE} || exit 1

ARTIFACTS_DIR="${CURRENT_DIR}/artifacts"
if [ -d "${ARTIFACTS_DIR}" ]; then
    rm -rf "${ARTIFACTS_DIR}/"
    echo "old ${ARTIFACTS_DIR} successfully removed"
fi

#building 
###################################################################################################
echo "Creating distribution of ${PACKAGE_FULLNAME} for ${OS_TYPE} v${OS_VERSION}"

#copy dist to build dir

#TODO: ...

#copy .spec file to create rpm
cp -f "${CURRENT_DIR}/build/packaging/rpm/aeca.spec" "${BUILD_DIR_SPECS}/${PACKAGE_FULLNAME}.spec" || exit 1

#add actual data to .spec
if ! [ -f "${BUILD_DIR_SPECS}/${PACKAGE_FULLNAME}.spec" ]; then
    echo "[ERROR] ${BUILD_DIR_SPECS}/${PACKAGE_FULLNAME}.spec not found!"
    exit 1
fi

sed -i "s|aeca_name|${NAME}|" "${BUILD_DIR_SPECS}/${PACKAGE_FULLNAME}.spec"
sed -i "s|aeca_version|${MAJOR}.${MINOR}|" "${BUILD_DIR_SPECS}/${PACKAGE_FULLNAME}.spec"
sed -i "s|aeca_release|${BUILD}|" "${BUILD_DIR_SPECS}/${PACKAGE_FULLNAME}.spec"

sed -i "s|aeca-dist|${PACKAGE_FULLNAME}|g" "${BUILD_DIR_SPECS}/${PACKAGE_FULLNAME}.spec"
echo "successfully modifyed ${BUILD_DIR_SPECS}/${PACKAGE_FULLNAME}.spec "

#cleanup dist
#remove readmes
find "${BUILD_DIR_TMP_PACKAGE}" -type f -iname "*.md" -exec rm {} \;

#remove some IDEA files.
find "${BUILD_DIR_TMP_PACKAGE}" -type f -iname "*.iml" -exec rm {} \;
find "${BUILD_DIR_TMP_PACKAGE}" -type f -iname ".gitignore" -exec rm {} \;
echo "extra files cleaned up"

cd ${BUILD_DIR}
#rpmbuild -vv -bb "${BUILD_DIR_SPECS}/${PACKAGE_FULLNAME}.spec" || exit 1
echo "[SUCCESS] ${PACKAGE_FULLNAME} created!"

if ! [ -d "${ARTIFACTS_DIR}" ]; then
    mkdir --verbose ${ARTIFACTS_DIR}    
fi

#find ${BUILD_DIR} -name "*${PACKAGE_FULLNAME}*" -not -name ".spec" -exec cp {} ${ARTIFACTS_DIR}/ \;
#find ${BUILD_DIR} -name "*${PACKAGE_FULLNAME}*.tar.gz" -exec cp {} ${ARTIFACTS_DIR}/ \;
#find ${BUILD_DIR} -name "*.rpm*" -exec cp {} ${ARTIFACTS_DIR}/ \;

echo "Packaging distribution of ${PACKAGE_FULLNAME}.rpm"