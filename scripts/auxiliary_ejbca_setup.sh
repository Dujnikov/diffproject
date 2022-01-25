#!/bin/bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source $ABSDIR/config.sh

if [ -z "${aeca_user}" ]; then
	aeca_user="aeca"
fi

if [ -z "${aeca_install_directory}" ]; then
	aeca_install_directory="/opt/aeca"
fi

if [ -z "${aeca_ejbca_home}" ]; then
	aeca_ejbca_home="${aeca_install_directory}/ejbca"
fi

if [ -z "${aeca_appserver_home}" ]; then
	aeca_appserver_home="${aeca_install_directory}/wildfly"
fi

if [ -z "${ejbca_version}" ]; then
	ejbca_version="ejbca_ce_7_4_3_2"
fi

### EJBCA install functions ###

ejbca_deploy_check() {  
  DURATION_SECONDS=30
  if [ ! -z "$1" ]; then
    DURATION_SECONDS="$1"
  fi
  DURATION=$(echo "$DURATION_SECONDS / 5" | bc)
  echo "wait ${DURATION_SECONDS}s for deploying EJBCA"
  
  for i in `seq 1 $DURATION`; do
    if [ -f "${aeca_appserver_home}/standalone/deployments/ejbca.ear.deployed" ]; then
      echo "EJBCA deployed"
      return 0
    fi
    sleep 5
  done
  echo "EJBCA not deployed after ${DURATION_SECONDS}s, exit"
  exit 1
}

ejbca_unpack(){
    if [ -f "${aeca_install_directory}/${ejbca_version}" ]; then
        rm -rf "${aeca_install_directory}/${ejbca_version}"
    fi

    if [ -f "$ABSDIR/../dist/${ejbca_version}.tar.gz" ]; then
        tar xvfz "$ABSDIR/../dist/${ejbca_version}.tar.gz" -C ${aeca_install_directory} || exit 1
        echo "${ejbca_version} unpacked from local storage to ${aeca_install_directory}"
    else
        echo "$ABSDIR/../dist/${ejbca_version}.tar.gz not found, trying to download it"
        wget "https://sourceforge.net/projects/ejbca/files/ejbca7/${ejbca_version}.zip/download" -O "${aeca_install_directory}/${ejbca_version}.zip" || exit 1        
        unzip -q "${ejbca_version}.zip" -d "${aeca_appserver_home}/${ejbca_version}"
        rm -f "${aeca_install_directory}/${ejbca_version}.zip"
        echo "${ejbca_version} downloaded and unpacked from network"
    fi

    if [ -h "${aeca_ejbca_home}" ]; then
        rm -f "${aeca_ejbca_home}"
    fi
    ln -snf "${aeca_install_directory}/${ejbca_version}" "${aeca_ejbca_home}"

    if ! [ -d "${aeca_install_directory}/${ejbca_version}" ]; then
        echo "[ERROR] Unpacking error"
        exit 1
    fi
}

ejbca_copy_properties(){
    if [ -d "$ABSDIR/../properties" ]; then
        #not required if custom exist
        cp -RTv "$ABSDIR/../properties" "${aeca_ejbca_home}/conf/" || exit 1
        
        #may be only in custom
        mkdir -p "${aeca_ejbca_home}/../ejbca-custom/conf/"
        cp -RTv "$ABSDIR/../properties" "${aeca_ejbca_home}/../ejbca-custom/conf/" || exit 1
    else
        echo "[ERROR] Properties not found!"
        exit 1
    fi
}

deploy_ejbca(){    
    su -s /bin/bash -c "cd ${aeca_ejbca_home} && ant -q clean deployear" ${aeca_user} || exit 1
}

install_ejbca(){
        su -s /bin/bash -c "cd ${aeca_ejbca_home} && ant runinstall" ${aeca_user} || exit 1
}

deploy_keys(){    
    su -s /bin/bash -c "cd ${aeca_ejbca_home} && ant deploy-keystore" ${aeca_user} || exit 1 
    cp -a ${aeca_ejbca_home}/p12 ${aeca_install_directory}
    cp -a ${aeca_ejbca_home}/p12 "${aeca_ejbca_home}/../ejbca-custom/"
}

echo "Unpacking EJBCA..."        
ejbca_unpack || exit 1
echo "Done."

echo "Copying propertoes..."        
ejbca_copy_properties || exit 1
echo "Done."

chown -R ${aeca_user}:${aeca_user} ${aeca_install_directory}
chmod -R u=rwX,g=rwX,o=rX ${aeca_install_directory}

echo "Deploying EJBCA..."        
deploy_ejbca || exit 1
ejbca_deploy_check 300 || exit 1
echo "Done."

echo "EJBCA initialization..."
install_ejbca || exit 1
echo "Done."

echo "Deploying keystore..."
deploy_keys || exit 1
echo "Done."

ejbca_deploy_check || exit 1
echo "=================================================="
echo "[SUCCESS] ${ejbca_version} successfully installed"