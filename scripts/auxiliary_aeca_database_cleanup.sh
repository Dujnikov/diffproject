#!/bin/bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source $ABSDIR/config.sh

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

drop_tables_aeca() {
    if [ ${aeca_database_type} = "mariadb" ]; then
        script_name="drop-tables-aeca-mysql.sql"
        if [ -f "$ABSDIR/../dist/sql/${script_name}" ]; then
            cat "$ABSDIR/../dist/sql/${script_name}" | mysql --host=${aeca_database_host} --user=${aeca_database_username} --password=${aeca_database_password} ${aeca_database_name} -f 
        else
            echo "[ERROR] Can not find $ABSDIR/../dist/sql/${script_name} !"
            exit 1
        fi
    elif [ ${aeca_database_type} = "postgres" ]; then
        script_name="drop-tables-aeca-postgres.sql"
        if [ -f "$ABSDIR/../dist/sql/${script_name}" ]; then
             cat "$ABSDIR/../dist/sql/${script_name}" | psql "postgresql://${aeca_database_username}:${aeca_database_password}@${aeca_database_host}/${aeca_database_name}"            
        else
            echo "[ERROR] Can not find $ABSDIR/../dist/sql/${script_name} !"
            exit 1
        fi
    else
        echo "[ERROR] Database type not specified"
    fi
}

echo "Preparing database ${aeca_database_host}:${aeca_database_port}/${aeca_database_name}, using DB user ${aeca_database_username}, who should have privileges to do that"

echo "Drop tables..."
drop_tables_aeca || exit 1
echo "Done."

echo "=================================================="
echo "[SUCCESS] database ${aeca_database_host}:${aeca_database_port}/${aeca_database_name} successfully prepared"
