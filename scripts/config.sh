#!/bin/bash

# Configurables
aeca_httpsserver_hostname="localhost"
aeca_install_directory="/opt/aeca"
aeca_appserver_home="${aeca_install_directory}/wildfly"
aeca_ejbca_home="${aeca_install_directory}/ejbca"
aeca_user="aeca"

#TODO only test purposes
aeca_database_type="mariadb"
#aeca_database_type="postgres"

aeca_database_username="aeca"
aeca_database_password="aeca"
aeca_database_host="localhost"
aeca_database_port="3306"
#aeca_database_port="5432"
aeca_database_name="aecatest"

# Variables that should not be configured
aeca_BASE_DN="O=Aladdin test AECA CA,C=RU"
aeca_superadmin_cn="InitialAdmin"
aeca_ca_name="ManagementTestCA"
aeca_ca_dn="CN=${aeca_ca_name},${aeca_BASE_DN}"

ejbca_version="ejbca_ce_7_4_3_2"
wildfly_version="wildfly-18.0.0.Final"
mariadb_client_version="mariadb-java-client-2.3.0"
postgres_client_version="postgresql-42.2.9"

AECA_DEBUG="true"

if [ ${aeca_database_type} = "mariadb" ]; then
  #this is local dev stand conf  
  aeca_database_driver="org.mariadb.jdbc.Driver"  
  aeca_database_driver_file="mariadb-java-client.jar"
  aeca_database_jdbctype="mysql"
  aeca_database_client_version=${mariadb_client_version}
    
elif [ ${aeca_database_type} = "postgres" ]; then
  #this is test stand conf  
  aeca_database_driver="org.postgresql.Driver"    
  aeca_database_driver_file="postgresql.jar"
  aeca_database_jdbctype="postgresql"
  aeca_database_client_version=${postgres_client_version}
else
  echo "[ERROR] Database type not specified"
  exit 1
fi