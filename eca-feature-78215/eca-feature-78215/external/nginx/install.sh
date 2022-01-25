#!/bin/bash

echo Обновляем систему и устанваливаем nginx!
#dnf update && dnf upgrade
dnf install nginx
systemctl start nginx
systemctl enable nginx
mkdir -p /var/www/html/default
chown nginx:nginx /var/www/html/default
touch /var/www/html/default/index.html
printf "<h2>It works!</h2>" > /var/www/html/default/index.html
mkdir /etc/nginx/certs
cp /etc/nginx/nginx.conf /etc/nginx/nginx.conf.bak && rm /etc/nginx/nginx.conf
cp nginx.conf /etc/nginx/
cd /etc/nginx/certs
echo Создаем ключи.
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
systemctl stop nginx && systemctl start nginx
echo Вот и все. Nginx и ключи установлены.


