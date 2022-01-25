#!/bin/bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source $ABSDIR/config.sh

if ! [ ${aeca_database_type} = "mariadb" ]; then
    AECA_DEBUG="true"
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

if [ -z "${aeca_database_jdbctype}" ]; then
	aeca_database_jdbctype="mysql"
fi

if [ -z "${aeca_database_username}" ]; then
	aeca_database_username="ejbca"
fi

if [ -z "${aeca_database_password}" ]; then
	aeca_database_password="ejbca"
fi

if [ -z "${wildfly_version}" ]; then
	wildfly_version="wildfly-18.0.0.Final"
fi

if [ -z "${aeca_database_driver}" ]; then
	aeca_database_driver="org.mariadb.jdbc.Driver"  
fi

if [ -z "${mariadb_client_version}" ]; then
	mariadb_client_version="mariadb-java-client-2.3.0"
fi

if [ -z "${aeca_database_driver_file}" ]; then
	aeca_database_driver_file="mariadb-java-client.jar"
fi

if [ -z "${aeca_database_client_version}" ]; then	
    aeca_database_client_version=${mariadb_client_version}
fi

ejbca_datasource="ejbcads"
wildfly_memory_size="2048"

### Service functions ###
if [ -f "$ABSDIR/../properties/web.properties" ]; then
    keystore_password=$(grep '^httpsserver.password' $ABSDIR/../properties/web.properties | awk -F= '{ print $2 }' | grep -v '^$')
    truststore_password=$(grep '^java.trustpassword' $ABSDIR/../properties/web.properties | awk -F= '{ print $2 }' | grep -v '^$')
    web_hostname=$(grep '^httpsserver.hostname' $ABSDIR/../properties/web.properties | awk -F= '{ print $2 }' | grep -v '^$')
else
    echo "[ERROR] $ABSDIR/../properties/web.properties not found!"
    exit 1
fi

wildfly_exec() {
    "${aeca_appserver_home}/bin/jboss-cli.sh" --connect "$1" || exit 1
    
    if [ ${AECA_DEBUG} = "true" ]; then
        echo "[DEBUG]: ${1}" 
    fi
}

wildfly_shutdown() {  
    "${aeca_appserver_home}/bin/jboss-cli.sh" --connect command=:shutdown
}


wildfly_reload() {  
    "${aeca_appserver_home}/bin/jboss-cli.sh" --connect command=:reload
}

wildfly_check() {
    DURATION_SECONDS=60
    if [ ! -z "$1" ]; then
        DURATION_SECONDS="$1"
    fi
    DURATION=$(echo "$DURATION_SECONDS / 5" | bc)

    echo "waiting ${DURATION_SECONDS}s for start up wildfly"  
    for i in `seq 1 $DURATION`; do
        "${aeca_appserver_home}/bin/jboss-cli.sh" --connect ":read-attribute(name=server-state)" | grep "result" | awk '{ print $3; }'|grep running
        if [ $? -eq 0 ]; then
            return 0
        fi
        sleep 5
    done
    echo "wildfly not started after ${DURATION_SECONDS}s, exit"
    exit 1
}

### Wildfly install functions ###

wildfly_unpack(){    
    
    if ! [ -d "${aeca_install_directory}" ]; then
        mkdir -p ${aeca_install_directory}
        echo "${aeca_install_directory} created"
    fi

    if [ -f "${aeca_install_directory}/${wildfly_version}" ]; then
        rm -rf "${aeca_install_directory}/${wildfly_version}"
    fi

    if [ -f "$ABSDIR/../dist/${wildfly_version}.tar.gz" ]; then
        tar xvfz "$ABSDIR/../dist/${wildfly_version}.tar.gz" -C ${aeca_install_directory} || exit 1
        echo "${wildfly_version} unpacked from local storage"
    else
        echo "$ABSDIR/../dist/${wildfly_version}.tar.gz not found, trying ro download it"
        wget "https://download.jboss.org/wildfly/18.0.0.Final/${wildfly_version}.zip" -O "${aeca_install_directory}/${wildfly_version}.zip"
        unzip -q "${wildfly_version}.zip" -d "${aeca_appserver_home}/${wildfly_version}" || exit 1
        echo "${wildfly_version} downloaded and unpacked from network"
    fi

    chown -R ${aeca_user}:${aeca_user} ${aeca_install_directory}
    chmod -R u=rwX,g=rwX,o=rX ${aeca_install_directory}

    if [ -h "${aeca_appserver_home}" ]; then
        rm -f "${aeca_appserver_home}"
    fi
    ln -snf "${aeca_install_directory}/${wildfly_version}" "${aeca_appserver_home}"    

    if ! [ -d "${aeca_install_directory}/${wildfly_version}" ]; then
        echo "[ERROR] Unpacking error"
        exit 1
    fi
}

wildfly_preconfig(){    
    ### Remove RESTEasy-Crypto
    sed -i "s|.*org.jboss.resteasy.resteasy-crypto.*||" "${aeca_appserver_home}/modules/system/layers/base/org/jboss/as/jaxrs/main/module.xml"
    rm -rf "${aeca_appserver_home}/modules/system/layers/base/org/jboss/resteasy/resteasy-crypto"

    rm -f ${aeca_appserver_home}/bin/*.{bat,exe}

    ### Create a Custom Configuration    
    cp -fv "$ABSDIR/../dist/wildfly/standalone.conf" "${aeca_appserver_home}/bin/standalone.conf" || exit 1    
    chown ${aeca_user}:${aeca_user} ${aeca_appserver_home}
    chmod 664 "${aeca_appserver_home}/bin/standalone.conf"
    
    ### Set Allowed Memory Usage
    sed -i -e "s/<HEAP_SIZE>/${wildfly_memory_size}/g" "${aeca_appserver_home}/bin/standalone.conf"
    echo "wildfly default memory size set to ${wildfly_memory_size} MB"

    ### Set Transaction Node Id
    sed -i -e "s/<TX_NODE_ID>/$(od -A n -t d -N 1 /dev/urandom | tr -d ' ')/g" "${aeca_appserver_home}/bin/standalone.conf"       
}

wildfly_configure_credstore(){
    if [ -f "/usr/bin/wildfly_pass" ]; then
        rm -f /usr/bin/wildfly_pass
    fi
    
    #Create a Master Password
    echo '#!/bin/sh' > /usr/bin/wildfly_pass
    echo "echo '$(openssl rand -base64 24)'" >> /usr/bin/wildfly_pass
    chown ${aeca_user}:${aeca_user} /usr/bin/wildfly_pass
    chmod 700 /usr/bin/wildfly_pass

     if ! [ -f "/usr/bin/wildfly_pass" ]; then
        echo "[ERROR] Wildfly credential store configuring failed!"
        exit 1
    fi    
    
    #Create the Credential Store
    wildfly_exec "/subsystem=elytron/credential-store=defaultCS:add(location=credentials, relative-to=jboss.server.config.dir, credential-reference={clear-text=\"{EXT}/usr/bin/wildfly_pass\", type=\"COMMAND\"}, create=true)"
    echo "credential store added"

    wildfly_reload
    wildfly_check
}

wildfly_add_db(){
    if [ -f "$ABSDIR/../dist/db_connectors.tar.gz" ]; then
        tar xvfz "$ABSDIR/../dist/db_connectors.tar.gz" -C ${aeca_install_directory} || exit 1        
        chown -R ${aeca_user}:${aeca_user} ${aeca_install_directory}        
    else
        echo "[ERROR] WildFly database client not found at $ABSDIR/../dist/db_connectors.tar.gz"
    fi

    if [ -f "${aeca_install_directory}/${aeca_database_client_version}.jar" ]; then
        cp -fv "${aeca_install_directory}/${aeca_database_client_version}.jar" "${aeca_appserver_home}/standalone/deployments/${aeca_database_driver_file}" || exit 1
        chown -R ${aeca_user}:${aeca_user} "${aeca_appserver_home}/standalone/deployments/"
        echo "${aeca_database_client_version} added from local storage"
    else
        echo "[ERROR] WildFly database client not found at $ABSDIR/../dist/${aeca_database_client_version}.jar"
        exit 1
    fi
    wildfly_reload
    wildfly_check    

    ### Add Database Driver to datasource        
    wildfly_exec "/subsystem=elytron/credential-store=defaultCS:add-alias(alias=dbPassword, secret-value=\"${aeca_database_password}\")"
    wildfly_exec "data-source add --name=\"${ejbca_datasource}\" --driver-name=\"${aeca_database_driver_file}\" --connection-url=\"jdbc:${aeca_database_jdbctype}://${aeca_database_host}:${aeca_database_port}/${aeca_database_name}\" --jndi-name=\"java:/EjbcaDS\" --use-ccm=true --driver-class=\"${aeca_database_driver}\" --user-name=\"${aeca_database_username}\" --credential-reference={store=defaultCS, alias=dbPassword} --validate-on-match=true --background-validation=false --prepared-statements-cache-size=50 --share-prepared-statements=true --min-pool-size=5 --max-pool-size=150 --pool-prefill=true --transaction-isolation=TRANSACTION_READ_COMMITTED --check-valid-connection-sql=\"select 1;\""        
    echo "datasource added"
    wildfly_reload
    wildfly_check    
}

### Configure WildFly Remoting
wildfly_configure_remoting(){
    wildfly_exec "/subsystem=remoting/http-connector=http-remoting-connector:write-attribute(name=connector-ref,value=remoting)"
    wildfly_exec "/socket-binding-group=standard-sockets/socket-binding=remoting:add(port=\"4447\",interface=management)"
    wildfly_exec "/subsystem=undertow/server=default-server/http-listener=remoting:add(socket-binding=remoting,enable-http2=true)"    
    echo "remoting added"
    wildfly_reload
    wildfly_check
}

wildfly_setup_logging() {    
    #production!
    wildfly_exec "/subsystem=logging/logger=org.ejbca:add(level=INFO)"
    wildfly_exec "/subsystem=logging/logger=org.cesecore:add(level=INFO)"
    
    if [ ${AECA_DEBUG} = "true" ]; then
        #DEBUG log level - only for dev purposes!
        wildfly_exec "/subsystem=logging/logger=org.ejbca:write-attribute(name=level, value=DEBUG)"
        wildfly_exec "/subsystem=logging/logger=org.cesecore:write-attribute(name=level, value=DEBUG)"
        
        # Additional Logging Configuration - only for dev purposes!
        wildfly_exec "/subsystem=logging/logger=org.jboss:add(level=WARN)"
        wildfly_exec "/subsystem=logging/logger=org.cesecore.config.ConfigurationHolder:add(level=WARN)"
        wildfly_exec "/subsystem=logging/logger=org.hibernate.dialect.H2Dialect:add(level=ERROR)"
        wildfly_exec "/subsystem=logging/logger=org.wildfly:add(level=WARN)"   
    fi

    ### Remove Old Log Files older than 7 days    
    #find "${aeca_appserver_home}/standalone/log/" -type f -mtime +1 -name 'server.log*' -execdir rm -- '{}' \;    
    #TODO make this as cron job    
    echo "logging added"
    wildfly_reload
    wildfly_check
}

wildfly_setup_interfaces(){
    
    # Remove existing TLS and HTTP configuration
    wildfly_exec "/subsystem=undertow/server=default-server/http-listener=default:remove"
    wildfly_exec "/subsystem=undertow/server=default-server/https-listener=https:remove"
    wildfly_exec "/socket-binding-group=standard-sockets/socket-binding=http:remove"
    wildfly_exec "/socket-binding-group=standard-sockets/socket-binding=https:remove"
    echo "default configurations removed"
    wildfly_reload
    wildfly_check    

    # Add New Interfaces and Sockets
    # Add interfaces, using 0.0.0.0 to make it available for the world
    wildfly_exec "/interface=http:add(inet-address=\"0.0.0.0\")"
    wildfly_exec "/interface=httpspub:add(inet-address=\"0.0.0.0\")"
    wildfly_exec "/interface=httpspriv:add(inet-address=\"0.0.0.0\")"
    wildfly_exec "/interface=aecahttps:add(inet-address=\"0.0.0.0\")"
    echo "new interfaces added"      

    wildfly_exec "/socket-binding-group=standard-sockets/socket-binding=http:add(port=\"8080\",interface=\"http\")"
    wildfly_exec "/socket-binding-group=standard-sockets/socket-binding=httpspub:add(port=\"8442\", interface=\"httpspub\")"
    wildfly_exec "/socket-binding-group=standard-sockets/socket-binding=httpspriv:add(port=\"8443\",interface=\"httpspriv\")"   
    wildfly_exec "/socket-binding-group=standard-sockets/socket-binding=aecahttps:add(port=\"8888\",interface=\"aecahttps\")"   
    echo "sockets bindings added"
    wildfly_reload
    wildfly_check
    
    #Configure TLS
    wildfly_exec "/subsystem=elytron/credential-store=defaultCS:add-alias(alias=httpsKeystorePassword, secret-value=\"${keystore_password}\")"
    wildfly_exec "/subsystem=elytron/credential-store=defaultCS:add-alias(alias=httpsTruststorePassword, secret-value=\"${truststore_password}\")"
    wildfly_exec "/subsystem=elytron/key-store=httpsKS:add(path=\"keystore/keystore.jks\",relative-to=jboss.server.config.dir,credential-reference={store=defaultCS, alias=httpsKeystorePassword},type=JKS)"
    wildfly_exec "/subsystem=elytron/key-store=httpsTS:add(path=\"keystore/truststore.jks\",relative-to=jboss.server.config.dir,credential-reference={store=defaultCS, alias=httpsTruststorePassword},type=JKS)"
    wildfly_exec "/subsystem=elytron/key-manager=httpsKM:add(key-store=httpsKS,algorithm=\"SunX509\",credential-reference={store=defaultCS, alias=httpsKeystorePassword})"
    wildfly_exec "/subsystem=elytron/trust-manager=httpsTM:add(key-store=httpsTS)"
    wildfly_exec "/subsystem=elytron/server-ssl-context=httpspub:add(key-manager=httpsKM,protocols=[\"TLSv1.2\"],use-cipher-suites-order=false)"
    wildfly_exec "/subsystem=elytron/server-ssl-context=httpspriv:add(key-manager=httpsKM,protocols=[\"TLSv1.2\"],use-cipher-suites-order=false,trust-manager=httpsTM,need-client-auth=true)"
    wildfly_exec "/subsystem=elytron/server-ssl-context=aecahttps:add(key-manager=httpsKM,protocols=[\"TLSv1.2\"],use-cipher-suites-order=false,trust-manager=httpsTM,need-client-auth=true)"
    echo "TLS configured"    

    wildfly_exec "/subsystem=undertow/server=default-server/http-listener=http:add(socket-binding=\"http\", redirect-socket=\"httpspriv\")"
    wildfly_exec "/subsystem=undertow/server=default-server/https-listener=httpspub:add(socket-binding=\"httpspub\", ssl-context=\"httpspub\", max-parameters=2048)"
    wildfly_exec "/subsystem=undertow/server=default-server/https-listener=httpspriv:add(socket-binding=\"httpspriv\", ssl-context=\"httpspriv\", max-parameters=2048)"
    wildfly_exec "/subsystem=undertow/server=default-server/https-listener=aecahttps:add(socket-binding=\"aecahttps\", ssl-context=\"aecahttps\", max-parameters=2048)"
    echo "interfaces listeners added"    
    wildfly_reload    
    systemctl stop aeca.service
    echo "Wildfly is off now"    
    systemctl start aeca.service
    wildfly_check 300    

    # HTTP Protocol Behavior Configuration
    wildfly_exec "/system-property=org.apache.catalina.connector.URI_ENCODING:add(value=\"UTF-8\")"
    wildfly_exec "/system-property=org.apache.catalina.connector.USE_BODY_ENCODING_FOR_QUERY_STRING:add(value=true)"
    wildfly_exec "/system-property=org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH:add(value=true)"
    wildfly_exec "/system-property=org.apache.tomcat.util.http.Parameters.MAX_COUNT:add(value=2048)"
    wildfly_exec "/system-property=org.apache.catalina.connector.CoyoteAdapter.ALLOW_BACKSLASH:add(value=true)"
    wildfly_exec "/subsystem=webservices:write-attribute(name=wsdl-host, value=jbossws.undefined.host)"
    wildfly_exec "/subsystem=webservices:write-attribute(name=modify-wsdl-address, value=true)"
    echo "HTTP Protocol Behavior added"    
    wildfly_reload
    wildfly_check      
}

wildfly_optional_settings(){
    if ! [ ${AECA_DEBUG} = "true" ]; then
        # Remove Welcome Content
        #wildfly_exec "/subsystem=undertow/server=default-server/host=default-host/location="\/":remove()"
        #wildfly_exec "/subsystem=undertow/configuration=handler/file=welcome-content:remove()"
        #echo "welcome-content removed"
        #wildfly_reload
        #wildfly_check
        #rm -rf "${aeca_appserver_home}/welcome-content/"
        
        #Redirect to Application for Unknown URLs    
        #wildfly_exec "/subsystem=undertow/configuration=filter/rewrite=redirect-to-app:add(redirect=true,target="/ejbca/")"
        #wildfly_exec "/subsystem=undertow/server=default-server/host=default-host/filter-ref=redirect-to-app:add(predicate="method(GET) and not path-prefix(/ejbca,/crls,/certificates,/.well-known) and not equals({\%{LOCAL_PORT}, 4447})")"
        #wildfly_reload
        #Disable Management Web Console
        wildfly_exec "/core-service=management/management-interface=http-interface:write-attribute(name=console-enabled,value=false)"

        #Remove the ExampleDS Data Source
        wildfly_exec "/subsystem=ee/service=default-bindings:remove()"
        wildfly_exec "data-source remove --name=ExampleDS"
        echo "sample datasources removed"    
        wildfly_reload
        wildfly_check
    fi     

    #Remove Unneeded Subsystems and Extensions
    wildfly_exec "/subsystem=jdr:remove()"
    wildfly_exec "/subsystem=sar:remove()"
    wildfly_exec "/subsystem=jmx:remove()"
    wildfly_exec "/subsystem=pojo:remove()"
    wildfly_exec "/subsystem=ee-security:remove()"
    wildfly_exec "/subsystem=microprofile-metrics-smallrye:remove()"
    wildfly_exec "/subsystem=microprofile-health-smallrye:remove()"
    wildfly_exec "/subsystem=microprofile-opentracing-smallrye:remove()"
    wildfly_exec "/extension=org.wildfly.extension.microprofile.health-smallrye:remove()"
    wildfly_exec "/extension=org.wildfly.extension.microprofile.opentracing-smallrye:remove()"
    wildfly_exec "/extension=org.jboss.as.jdr:remove()"
    wildfly_exec "/extension=org.jboss.as.jmx:remove()"
    wildfly_exec "/extension=org.jboss.as.sar:remove()"
    wildfly_exec "/extension=org.jboss.as.pojo:remove()"
    wildfly_exec "/extension=org.wildfly.extension.ee-security:remove()"
    wildfly_exec "/subsystem=distributable-web:remove()"
    wildfly_exec "/subsystem=infinispan/cache-container=ejb:remove()"
    wildfly_exec "/subsystem=infinispan/cache-container=server:remove()"
    wildfly_exec "/subsystem=ejb3/cache=distributable:remove()"
    wildfly_exec "/subsystem=ejb3/passivation-store=infinispan:remove()"
    wildfly_exec "/subsystem=security/security-domain=jaspitest:remove()"    
    echo "extensions removed"
    wildfly_reload
    wildfly_check   
    
    #Increase the maximum size of POST requests to 25 MB
    wildfly_exec "/subsystem=undertow/server=default-server/http-listener=http/:write-attribute(name=max-post-size,value=25485760)"
    wildfly_reload
    wildfly_check

    #Performance Tuning
    wildfly_exec "/subsystem=io/worker=default/:write-attribute(name=task-core-threads,value=25)"
    wildfly_exec "/subsystem=io/worker=default/:write-attribute(name=task-max-threads,value=100)"
    wildfly_exec "/subsystem=io/worker=default/:write-attribute(name=io-threads,value=100)"
    wildfly_exec "/subsystem=ejb3/strict-max-bean-instance-pool=slsb-strict-max-pool:undefine-attribute(name=derive-size)"
    wildfly_exec "/subsystem=ejb3/strict-max-bean-instance-pool=slsb-strict-max-pool:write-attribute(name=max-pool-size, value=32)"    
    echo "performance settings are congigured"
    wildfly_reload
    wildfly_check
}

wildfly_enable_ajp() {
    wildfly_exec "/subsystem=undertow/server=default-server/ajp-listener=ajp-listener:add(socket-binding=ajp, scheme=https, enabled=true)"
    echo "ajp listener added"
    wildfly_reload
    wildfly_check
}

wildfly_killall() {
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

echo "Unpacking WildFly..."        
wildfly_unpack
echo "=================================================="
echo "Done."


echo "Preconfiguring WildFly..."        
wildfly_preconfig
echo "=================================================="
echo "Done."

echo "Adding WildFly's systemd aeca.service..."        
#Configure WildFly as a Service
cat <<EOF > "${aeca_appserver_home}/bin/launch.sh"
#!/bin/bash

if [ "x\$WILDFLY_HOME" = "x" ]; then
    WILDFLY_HOME="${aeca_appserver_home}"
fi

if [[ "\$1" == "domain" ]]; then
    \$WILDFLY_HOME/bin/domain.sh -c \$2 -b \$3
else
    \$WILDFLY_HOME/bin/standalone.sh -c \$2 -b \$3
fi
EOF

find "${aeca_appserver_home}/bin/" -type f -iname "*.sh" -exec chmod 4751 {} \;

 if ! [ -d "/var/run/aeca" ]; then
    mkdir /var/run/aeca
    chown ${aeca_user}:${aeca_user} "/var/run/aeca"
    chmod 775 "/var/run/aeca"
fi

if ! [ -d "/etc/aeca" ]; then
    mkdir "/etc/aeca" || exit 1
    cp -fv "$ABSDIR/../dist/wildfly/wildfly.conf" "/etc/aeca/wildfly.conf" || exit 1
    chown ${aeca_user}:${aeca_user} "/etc/aeca/wildfly.conf"
    chmod 555 "/etc/aeca/wildfly.conf"
fi

cat <<EOF > "/etc/systemd/system/aeca.service"
[Unit]
Description=The WildFly Application Server to AECA
After=syslog.target network.target
Before=httpd.service

[Service]
Environment=LAUNCH_JBOSS_IN_BACKGROUND=1
EnvironmentFile=-/etc/aeca/wildfly.conf
User=${aeca_user}
LimitNOFILE=102642
PIDFile=/run/aeca/aeca.pid
ExecStart=${aeca_appserver_home}/bin/launch.sh \$WILDFLY_MODE \$WILDFLY_CONFIG \$WILDFLY_BIND
StandardOutput=null

[Install]
WantedBy=multi-user.target
EOF

chmod 544 /etc/systemd/system/aeca.service
chown -R ${aeca_user}:${aeca_user} ${aeca_install_directory}
systemctl enable aeca.service
systemctl daemon-reload
systemctl start aeca.service
wildfly_check
echo "=================================================="
echo "Done"

echo "Adding WildFly Elytron credential store..."        
wildfly_configure_credstore || exit 1
echo "=================================================="
echo "Done."

echo "Adding WildFly database driver and datasource..."        
wildfly_add_db || exit 1
echo "=================================================="
echo "Done."

echo "Configuring WildFly remoting..."        
wildfly_configure_remoting || exit 1
echo "=================================================="
echo "Done."

echo "Configuring WildFly logging..."        
wildfly_setup_logging || exit 1
echo "=================================================="
echo "Done."

echo "Setup WildFly interfaces..."        
wildfly_setup_interfaces || exit 1
echo "=================================================="
echo "Done."

echo "WildFly optionals configuration..."        
wildfly_optional_settings || exit 1
echo "=================================================="
echo "Done."

echo "WildFly AJP enable..."        
wildfly_enable_ajp || exit 1
echo "=================================================="
echo "Done."

echo "=================================================="
echo "[SUCCESS] ${wildfly_version} successfully installed"