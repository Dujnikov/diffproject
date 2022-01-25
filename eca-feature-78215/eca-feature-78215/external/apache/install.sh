#!/bin/bash

echo Обновляем кэш системы и устанваливаем apache!
#dnf update && dnf upgrade
sed -i  's/SELINUX=enforcing/SELINUX=disabled/' /etc/selinux/config
dnf install httpd mod_ssl mod_proxy_html -y
systemctl start httpd.service
systemctl enable httpd.service
mkdir -p /var/www/html/default
chown apache:apache /var/www/html/default
touch /var/www/html/default/index.html
printf "<h2>It works!</h2>" > /var/www/html/default/index.html
mkdir /etc/httpd/certs/
cp ssl.conf   /etc/httpd/conf.d/
cp localhost.localdomain.conf /etc/httpd/conf.d/
cd /etc/httpd/certs/

echo Создаем ключи.

openssl genrsa -out ca.key 2048
openssl req -new -x509 -days 3650 -key ca.key -out ca.crt
openssl genrsa -out server.key 2048
openssl req -new -key server.key -out server.csr
openssl x509 -req -days 3650 -in server.csr -CA ca.crt -CAkey ca.key -set_serial 01 -out server.crt
openssl genrsa -out client.key 2048
openssl req -new -key client.key -out client.csr
openssl x509 -req -days 3650 -in client.csr -CA ca.crt -CAkey ca.key -set_serial 01 -out client.crt
cp /etc/httpd/certs/ca.crt /etc/pki/ca-trust/source/anchors
update-ca-trust force-enable
update-ca-trust extract
openssl pkcs12 -export -in client.crt -inkey client.key -out client.p12 -name "client"
systemctl stop httpd.service && systemctl start httpd.service

echo Вот и все. Apache и ключи установлены.
echo Ребутаем машину.
reboot
