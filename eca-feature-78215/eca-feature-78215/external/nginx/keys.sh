#!/bin/bash

function clear_keys {
clear
rm *.key *.crt *.csr *.p12
rm -R /etc/pki/ca-trust/source/anchors/ca.crt
update-ca-trust force-enable
update-ca-trust extract
echo "Ключи и сертификаты удалены."
}

function make_keys {
clear
openssl genrsa -out ca.key 2048
openssl req -new -x509 -days 3650 -key ca.key -out ca.crt
openssl genrsa -out server.key 2048
openssl req -new -key server.key -out server.csr
openssl x509 -req -days 3650 -in server.csr -CA ca.crt -CAkey ca.key -set_serial 01 -out server.crt
openssl genrsa -out client.key 2048
openssl req -new -key client.key -out client.csr
openssl x509 -req -days 3650 -in client.csr -CA ca.crt -CAkey ca.key -set_serial 01 -out client.crt
cp /etc/nginx/certs/ca.crt /etc/pki/ca-trust/source/anchors
update-ca-trust force-enable
update-ca-trust extract
openssl pkcs12 -export -in client.crt -inkey client.key -out client.p12 -name "client"
echo "Ключи и сертификаты созданы."
}

function menu {
clear
echo
echo -e "\t\t\tЧто нужно сделать?\n"
echo -e "\t1. Удалить ключи и сертификаты."
echo -e "\t2. Создать ключи и сертификаты."
echo -e "\t0. Выход"
echo -en "\t\tВведите номер раздела: "
read -n 1 option
}

while [ $? -ne 1 ]
do
        menu
        case $option in
0)
        break ;;
1)
        clear_keys ;;
2)
        make_keys ;;
*)
        clear
echo "Нужно выбрать что необходимо сделать с ключами и сертификатами.";;
esac
echo -en "\n\n\t\t\tНажмите любую клавишу для продолжения"
read -n 1 line
done
clear
