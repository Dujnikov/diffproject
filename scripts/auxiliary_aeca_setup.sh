#!/bin/bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source $ABSDIR/config.sh

if [ -z "${aeca_install_directory}" ]; then
	aeca_install_directory="/opt/aeca"
fi

if [ -z "${aeca_ejbca_home}" ]; then
	aeca_ejbca_home="${aeca_install_directory}/ejbca"
fi

if [ -z "${aeca_appserver_home}" ]; then
	aeca_appserver_home="${aeca_install_directory}/wildfly"
fi

### AECA install functions ###

aeca_unpack(){
  if [ -d "${aeca_install_directory}/aeca_plugins" ]; then      
    rm -rf "${aeca_install_directory}/aeca_plugins/*" || exit 1      
  else
    mkdir "${aeca_install_directory}/aeca_plugins" || exit 1      
  fi    

  if [ -f "$ABSDIR/../dist/aeca_plugins.tar.gz" ]; then              
    tar xvf "$ABSDIR/../dist/aeca_plugins.tar.gz" -C "${aeca_install_directory}/aeca_plugins" || exit 1    
  else
      echo "[ERROR] aeca_plugins.tar.gz not found!"
      exit 1
  fi  
}

aeca_ejbca_patch(){
    if [ -d "${aeca_install_directory}/aeca_plugins" ]; then
        cp -RTvf "${aeca_install_directory}/aeca_plugins" "${aeca_ejbca_home}/" || exit 1
    else
      echo "[ERROR] ${aeca_install_directory}/aeca_plugins not found"
      exit 1
    fi        
    
    if [ -d "${aeca_install_directory}/aeca_plugins" ]; then
        rm -rf "${aeca_install_directory}/aeca_plugins"
    fi    
}

aeca_deploy(){
  su -s /bin/bash -c "cd ${aeca_ejbca_home} && ant clean deployear" ${aeca_user} || exit 1   
}

deploy_check() {  
  DURATION_SECONDS=30
  if [ ! -z "$1" ]; then
    DURATION_SECONDS="$1"
  fi
  DURATION=$(echo "$DURATION_SECONDS / 5" | bc)
  echo "wait ${DURATION_SECONDS}s for deploying AECA"
  
  for i in `seq 1 $DURATION`; do
    if [ -f "${aeca_appserver_home}/standalone/deployments/ejbca.ear.deployed" ]; then
      echo "AECA successfully deployed"
      return 0
    fi
    sleep 5
  done
  echo "AECA not deployed after ${DURATION_SECONDS}s, exit"
  exit 1
}

echo "Unpacking AECA-plugins..."        
aeca_unpack || exit 1
echo "Done."
echo "========================================"

echo "Deploying AECA-plugins..."        
aeca_ejbca_patch || exit 1

chown -R ${aeca_user}:${aeca_user} ${aeca_install_directory}
chmod -R u=rwX,g=rwX,o=rX ${aeca_install_directory}

aeca_deploy
deploy_check 300
echo "Done."

echo "========================================"
echo "[SUCCESS] AECA successfully installed"