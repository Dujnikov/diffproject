#!/bin/bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source $ABSDIR/config.sh

echo "=================================================="
echo "DOWNLOAD AND INSTALL LIBERICA JDK 8"
echo "=================================================="

echo "Search and remove old version Liberica JDK"
rm -f /opt/bellsoft*.*

OS_VERSION=$(awk -F'=' '/ID_LIKE/ {print $2}' /etc/os-release | tr -d '"' | cut -d" " -f1)

echo 'Your OS version is '$OS_VERSION

if [ "$OS_VERSION" == "rhel" ]; then
        wget -P /opt https://download.bell-sw.com/java/8u312+7/bellsoft-jdk8u312+7-linux-amd64.rpm && yum install -y /opt/*.rpm
        else
          if [ "$OS_VERSION" == "debian" ]; then
             wget -P /opt https://download.bell-sw.com/java/8u312+7/bellsoft-jdk8u312+7-linux-amd64.deb && dpkg -i /opt/bellsoft*.deb
		else echo "Can't install Liberica JDK, please try it manually!"
          fi
fi

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

default_properties_setup(){	
  source $ABSDIR/auxiliary_default_properties_setup.sh || exit 1 
}

wildfly_setup(){
	source $ABSDIR/auxiliary_wildfly_setup.sh || exit 1
}

ejbca_setup(){
  source $ABSDIR/auxiliary_ejbca_database_setup.sh || exit 1  
	source $ABSDIR/auxiliary_ejbca_setup.sh	
}

aeca_setup(){
  source $ABSDIR/auxiliary_aeca_setup.sh
  source $ABSDIR/auxiliary_aeca_database_setup.sh || exit 1  
}

wildfly_killall(){
  pidof java > /dev/null 2> /dev/null
  if [ $? -eq 0 ]; then
    echo "There are Java processes running, make sure there is no WildFly, JBoss or Tomcat server already running, installation will fail if so."
    echo "Are you sure you want to continue?"
    select yn in "Yes" "No"; do
      case $yn in
          Yes ) echo "Continuing..."; break;;
          No ) exit;;
      esac
    done
    killall -9 java
    sleep 10
  fi
}

check_root () {
  if [[ $EUID -ne 0 ]]; then
    echo "This script must be run as root!" 
    exit 1
  fi
}

### INSTALLATION
echo "=================================================="
echo "ALADDIN ENTERPRISE CERTIFICATE AUTHORITY"
echo "=================================================="

check_root || exit 1

#remove old files
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

if [ -d "${aeca_install_directory}" ]; then
  rm -rf "${aeca_install_directory}"
  echo "${aeca_install_directory} successfully removed"
fi

#creating
if ! [ -d "${aeca_install_directory}" ]; then
  echo "${aeca_install_directory} created"
  mkdir ${aeca_install_directory} || exit 1  
fi

getent group ${aeca_user} >/dev/null 2>&1 || \
		groupadd -r ${aeca_user}
getent passwd ${aeca_user} >/dev/null 2>&1 || \
		useradd -g ${aeca_user} -d ${aeca_install_directory} -s /bin/bash -r ${aeca_user}

echo "user ${aeca_user} added"

chown -R ${aeca_user}:${aeca_user} ${aeca_install_directory}
chmod -R u=rwX,g=rwX,o=rX ${aeca_install_directory}

echo "install directory ${aeca_install_directory} prepared"

#changing default properties
echo "=================================================="
echo "To install the Product correctly, you need to have correct EJBCA configuration files in $ABSDIR/../properties/"
echo "Type [Yes] if you want to overwrite default template config files in $ABSDIR/../dist/properties/ by values from $ABSDIR/config.sh"
echo "Type [No] if you already have correct config files in $ABSDIR/../dist/properties/ and no need to overwrite it"
echo "Type [Cancel] to cancel this installation"
select yn in "Yes" "No" "Cancel"; do
  case $yn in
      Yes ) default_properties_setup; break;;
      No ) echo "Using an existing files in $ABSDIR/../config"; break;;
      Cancel ) exit;;
  esac
done
echo "=================================================="
echo "SUCCESS"

#setup wildfly
echo "=================================================="
echo "To install the Product correctly, you need to have installed WildFly Application Server"
echo "Type [Yes] if you want to install WildFly to ${aeca_appserver_home}"
echo "Type [No] if you already have installed and configured WildFly in ${aeca_appserver_home} and want to save it"
echo "Type [Cancel] to cancel this installation"
select yn in "Yes" "No" "Cancel"; do
  case $yn in
      Yes ) wildfly_killall; wildfly_setup; break;;
      No ) echo "Using an existing ${aeca_appserver_home}"; break;;
      Cancel ) exit;;
  esac
done
echo "=================================================="
echo "SUCCESS"

#setup EJBCA
echo "=================================================="
echo "To install the Product correctly, you need to have installed EJBCA"
echo "Type [Yes] if you want to install EJBCA to ${aeca_ejbca_home}"
echo "Type [No] if you already have configured EJBCA in ${aeca_ejbca_home} and want to save it"
echo "Type [Cancel] to cancel this installation"
select yn in "Yes" "No" "Cancel"; do
  case $yn in
      Yes ) ejbca_setup; break;;
      No ) echo "Using an existing ${aeca_ejbca_home}"; break;;
      Cancel ) exit;;
  esac
done
echo "=================================================="
echo "SUCCESS"

#setup aeca-plugins
echo "=================================================="
echo "Do you really want to install AECA?"
echo "Type [Yes] if you want to install AECA to ${aeca_ejbca_home}"
echo "Type [No] if you want to use only ${ejbca_version} without any AECA possibilities"
echo "Type [Cancel] to cancel this installation"
select yn in "Yes" "No" "Cancel"; do
  case $yn in
      Yes ) aeca_setup; break;;
      No ) echo "Using an existing ${aeca_ejbca_home}"; break;;
      Cancel ) exit;;
  esac
done
echo "=================================================="
echo "SUCCESS"
chown -R ${aeca_user}:${aeca_user} ${aeca_install_directory}

echo "restarting aeca.service"
systemctl restart aeca.service
echo "INSTALLATION COMPLETED!"

echo "=================================================="

cat ${aeca_install_directory}/ejbca-custom/conf/web.properties | grep superadmin.password > ${aeca_install_directory}/generated_passwords.txt
superadmin_password=$(grep '^superadmin.password' ${aeca_install_directory}/generated_passwords.txt | awk -F= '{ print $2 }' | grep -v '^$')
echo "You can now install the keystore, from ${aeca_install_directory}/p12, in your web browser, using the password " $superadmin_password
echo "You can find all the generated passwords in the file ${aeca_install_directory}/generated_passwords.txt"
