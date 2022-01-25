#!/bin/bash
# TODO: данный скрипт должен осуществлять сборку всех компонентов и подсистем решения в .deb-пакет

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

echo "Packaging distribution of ${PACKAGE_FULLNAME}.deb"


#change version
###################################################################################################
#if [ -f "/etc/os-release" ]; then
#    OS_VERSION=`cat /etc/os-release | sed -n 's/.*AstraLinux.\?(\([0-9 \.]*\)).*/\1/p'`
#
#    if [ "$OS_VERSION" == "1.7" ]; then
#	    OS_RES_DIR_NAME="DebCommon"
#    fi
#
#	OS_TYPE="Astra"
#	OS_SHORT_NAME="al"
#else
#    echo "[ERROR] Can't define current platform!"
#    #exit 1
#fi


#infrastructure for building
###################################################################################################
CURRENT_DIR=$(pwd)
echo "Working at ${CURRENT_DIR}"

BUILD_DIR="${CURRENT_DIR}/build/aeca/DEBIAN"
SOURCE_DIR="${CURRENT_DIR}/build/aeca/"
if [ -d "${BUILD_DIR}" ]; then
    rm -rf "${BUILD_DIR}"
    echo "old ${BUILD_DIR} successfully removed"
fi

mkdir -p --verbose ${BUILD_DIR} || exit 1

#building 
###################################################################################################
echo "Creating distribution of ${PACKAGE_FULLNAME} for ${OS_TYPE} ${OS_VERSION}"

#copy control file to dir
cp "${CURRENT_DIR}/build/packaging/deb/"control "${BUILD_DIR}" || exit 1
echo  "${CURRENT_DIR}/build/packaging/deb/control successfully copied to ${BUILD_DIR}"

#copy postinst file to dir
cp "${CURRENT_DIR}/build/packaging/deb/"postinst "${BUILD_DIR}" || exit 1
echo  "${CURRENT_DIR}/build/packaging/deb/postinst successfully copied to ${BUILD_DIR}"

#copy preinst file to dir
cp "${CURRENT_DIR}/build/packaging/deb/"preinst "${BUILD_DIR}" || exit 1
echo  "${CURRENT_DIR}/build/packaging/deb/preinst successfully copied to ${BUILD_DIR}"

#copy src to build dir
cp -RTf "${CURRENT_DIR}/build/dist/" "${SOURCE_DIR}" || exit 1
echo "${CURRENT_DIR}/src successfully copied to ${BUILD_DIR}/dist"

sed -i -e "s/Version: 1.0.0.0/Version: $VERSION/g" 	"${BUILD_DIR}/control"

find "${SOURCE_DIR}" -type f -iname "*.md" -exec rm {} \;

cd "${BUILD_DIR}"

chmod 775 preinst
chmod 775 postinst

cd "${CURRENT_DIR}/build/"
fakeroot dpkg-deb --build aeca || exit 1
echo "[SUCCESS] ${PACKAGE_FULLNAME} created!"

mv aeca.deb ${PACKAGE_FULLNAME}.deb

ARTIFACTS_DIR="${CURRENT_DIR}/artifacts1"
if [ -d "${ARTIFACTS_DIR}" ]; then
    rm -rf "${ARTIFACTS_DIR}/"
    echo "old ${ARTIFACTS_DIR} successfully removed"
fi

if ! [ -d "${ARTIFACTS_DIR}" ]; then
    mkdir --verbose ${ARTIFACTS_DIR}    
fi

find ${BUILD_DIR} -name "*${PACKAGE_FULLNAME}*.deb" -exec cp {} ${ARTIFACTS_DIR}/ \;
find "${CURRENT_DIR}/build/" -name "*.deb*" -exec cp {} ${ARTIFACTS_DIR}/ \;
