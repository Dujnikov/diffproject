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

if [ -z "${aeca_httpsserver_hostname}" ]; then
	aeca_httpsserver_hostname="localhost"
fi

if [ -z "${aeca_BASE_DN}" ]; then
	aeca_BASE_DN="O=Example CA,C=SE"
fi

if [ -z "${aeca_superadmin_cn}" ]; then
	aeca_superadmin_cn="SuperAdmin"
fi

if [ -z "${aeca_ca_name}" ]; then
	aeca_ca_name="ManagementCA"
fi

if [ -z "${aeca_ca_dn}" ]; then
	aeca_ca_dn="CN=${aeca_ca_name},${aeca_BASE_DN}"
fi

if [ -z "${aeca_database_type}" ]; then
	aeca_database_type="mariadb"    
fi

if [ -z "${aeca_database_host}" ]; then
	aeca_database_host="localhost"
fi

if [ -z "${aeca_database_port}" ]; then
	aeca_database_port="3306"
fi

if [ -z "${aeca_database_name}" ]; then
	aeca_database_name="ejbcatest"
fi

if [ -z "${aeca_database_username}" ]; then
	aeca_database_username="ejbca"
fi

if [ -z "${aeca_database_password}" ]; then
	aeca_database_password="ejbca"
fi

if [ -z "${aeca_database_driver}" ]; then
	aeca_database_driver="org.mariadb.jdbc.Driver"    
fi

pwgen() {
  NEW_PASSWORD=$(dd if=/dev/urandom bs=1 count=64 2> /dev/null | sha1sum | awk '{print $1}' | tr -d "\n")
  if [ -z "$NEW_PASSWORD" ]; then
    echo "Created empty password - very bad"
    exit 1
  fi
  echo -n "${NEW_PASSWORD}"
}

echo "Generating passwords..."
passwordencryptionkey=$(pwgen)
cakeystorepass=$(pwgen)
cmskeystorepass=$(pwgen)
truststorepass=$(pwgen)
httpsserver_password=$(pwgen)
superadmin_password=$(pwgen)

#generated_passwords.txt
if [ -f "${aeca_install_directory}/generated_passwords.txt" ]; then
	rm -f "${aeca_install_directory}/generated_passwords.txt"
fi

cat <<EOF > "${aeca_install_directory}/generated_passwords.txt"
admin_password=${superadmin_password}
EOF
echo "Passwords are written at ${aeca_install_directory}/generated_passwords.txt"

################################################################################

echo "Patching configs..."

#cesecore.properties
if [ -f "$ABSDIR/../properties/cesecore.properties" ]; then
	sed -i "s/password.encryption.key=CHANGEIT/password.encryption.key=${passwordencryptionkey}/" $ABSDIR/../properties/cesecore.properties
	sed -i "s/ca.keystorepass=CHANGEIT/ca.keystorepass=${cakeystorepass}/" $ABSDIR/../properties/cesecore.properties
	echo "Done $ABSDIR/../properties/cesecore.properties"
else
	echo "could not find $ABSDIR/../properties/cesecore.properties"
	exit 1
fi

################################################################################

#web.properties
if [ -f "$ABSDIR/../properties/web.properties" ]; then
	sed -i "s/java.trustpassword=CHANGEIT/java.trustpassword=${truststorepass}/" $ABSDIR/../properties/web.properties
	sed -i "s/httpsserver.password=CHANGEIT/httpsserver.password=${httpsserver_password}/" $ABSDIR/../properties/web.properties
	sed -i "s/superadmin.password=CHANGEIT/superadmin.password=${superadmin_password}/" $ABSDIR/../properties/web.properties
	sed -i "s/superadmin.cn=CHANGEIT/superadmin.cn=${aeca_superadmin_cn}/" $ABSDIR/../properties/web.properties
	sed -i "s/superadmin.dn=CHANGEIT/superadmin.dn=CN=${aeca_superadmin_cn},${aeca_BASE_DN}/" $ABSDIR/../properties/web.properties
	sed -i "s/httpsserver.hostname=CHANGEIT/httpsserver.hostname=${aeca_httpsserver_hostname}/" $ABSDIR/../properties/web.properties
	sed -i "s/httpsserver.dn=CHANGEIT/httpsserver.dn=CN=${aeca_httpsserver_hostname},${aeca_BASE_DN}/" $ABSDIR/../properties/web.properties
	echo "Done $ABSDIR/../properties/web.properties"
else
	echo "could not find $ABSDIR/../properties/web.properties"
	exit 1
fi

################################################################################

#database.properties
if [ -f "$ABSDIR/../properties/database.properties" ]; then	
	if [ ${aeca_database_type} = "mariadb" ]; then
		#this is local dev stand conf
		sed -i "s|database.name=CHANGEIT|database.name=mysql|" $ABSDIR/../properties/database.properties
		sed -i "s|database.url=CHANGEIT|database.url=jdbc:mysql://${aeca_database_host}:${aeca_database_port}/${aeca_database_name}?characterEncoding=UTF-8|" $ABSDIR/../properties/database.properties
	elif [ ${aeca_database_type} = "postgres" ]; then
		#this is test stand conf
		sed -i "s|database.name=CHANGEIT|database.name=postgres|" $ABSDIR/../properties/database.properties
		sed -i "s|database.url=CHANGEIT|database.url=jdbc:postgresql://${aeca_database_host}:${aeca_database_port}/${aeca_database_name}?characterEncoding=UTF-8|" $ABSDIR/../properties/database.properties
	else
		echo "[ERROR] Database type not specified"
		exit 1
	fi
	sed -i "s|database.driver=CHANGEIT|database.driver=${aeca_database_driver}|" $ABSDIR/../properties/database.properties
	sed -i "s|database.username=CHANGEIT|database.username=${aeca_database_username}|" $ABSDIR/../properties/database.properties
	sed -i "s|database.password=CHANGEIT|database.password=${aeca_database_password}|" $ABSDIR/../properties/database.properties
	#sed -i "s/database.password=CHANGEIT/database.password=NOT_SHOWN/" $ABSDIR/../properties/database.properties
	echo "Done $ABSDIR/../properties/database.properties"
else
	echo "could not find $ABSDIR/../properties/database.properties"
	exit 1
fi

################################################################################

#ejbca.properties
if [ -f "$ABSDIR/../properties/ejbca.properties" ]; then
	sed -i "s|appserver.home=CHANGEIT|appserver.home=${aeca_appserver_home}|" $ABSDIR/../properties/ejbca.properties
	sed -i "s|ca.cmskeystorepass=CHANGEIT|ca.cmskeystorepass=${cmskeystorepass}|" $ABSDIR/../properties/ejbca.properties
	echo "Done $ABSDIR/../properties/ejbca.properties"
else
	echo "could not find $ABSDIR/../properties/ejbca.properties"
	exit 1
fi

################################################################################

#install.properties
if [ -f "$ABSDIR/../properties/install.properties" ]; then
	sed -i "s|ca.name=CHANGEIT|ca.name=${aeca_ca_name}|" $ABSDIR/../properties/install.properties
	sed -i "s|ca.dn=CHANGEIT|ca.dn=${aeca_ca_dn}|" $ABSDIR/../properties/install.properties
	echo "Done $ABSDIR/../properties/install.properties"
else
	echo "could not find $ABSDIR/../properties/install.properties"
	exit 1
fi