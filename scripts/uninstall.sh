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

check_root () {
  if [[ $EUID -ne 0 ]]; then
    echo "This script must be run as root!" 
    exit 1
  fi
}

aeca_database_cleanup(){
	source $ABSDIR/auxiliary_aeca_database_cleanup.sh || exit 1  
}

ejbca_database_cleanup(){
	source $ABSDIR/auxiliary_ejbca_database_cleanup.sh || exit 1  
}

check_root

#changing default properties
echo "=================================================="
echo "LAST CHANCE TO STOP THIS"
echo "You want to fully remove AECA and it's dependencies"
echo "Are you sure you want to continue?"

select yn in "Yes" "No"; do
    case $yn in        
        Yes ) echo "Removing AECA..."; break;;
        No ) exit;;
    esac
done

#aeca database remove
aeca_database_cleanup
echo "aeca database successfully removed"

#ejbca database remove
ejbca_database_cleanup
echo "ejbca database successfully removed"

#aeca directory remove
if [ -d "${aeca_install_directory}" ]; then
    rm -rf "${aeca_install_directory}"
	echo "${aeca_install_directory} successfully removed"
fi

#service remove
if [ -d "/var/run/aeca" ]; then
    rm -rf "/var/run/aeca"
	echo "/var/run/aeca successfully removed"
fi

if [ -f "/etc/systemd/system/aeca.service" ]; then
	systemctl stop aeca.service
	systemctl disable aeca.service
    rm -rf "/etc/systemd/system/aeca.service"
	systemctl daemon-reload
	systemctl reset-failed
	echo "/etc/systemd/system/aeca.service successfully removed"
fi

if [ -d "/etc/aeca" ]; then
	rm -rf "/etc/aeca"
	echo "/etc/aeca successfully removed"
fi

if [ -f "/usr/bin/wildfly_pass" ]; then
	rm -rf "/usr/bin/wildfly_pass"
	echo "/usr/bin/wildfly_pass removed"
fi

if getent passwd ${aeca_user} >/dev/null 2>&1; then
	userdel ${aeca_user}
fi

if getent group ${aeca_user} >/dev/null 2>&1; then
	groupdel ${aeca_user}
fi
echo "${aeca_user} successfully removed"

echo "=================================================="
echo "UNINSTALL COMPLETED!"