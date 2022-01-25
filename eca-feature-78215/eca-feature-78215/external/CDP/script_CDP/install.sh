#!/bin/sh
dnf update -y && dnf install -y nginx php-fpm php
systemctl enable nginx && service nginx start
sed -i  's/;upload_tmp_dir =/upload_tmp_dir = \/tmp/' /etc/php.ini
sed -i  's/upload_max_filesize = 2M/upload_max_filesize = 32M/' /etc/php.ini
sed -i  's/max_file_uploads = 20/max_file_uploads = 1/' /etc/php.ini
sed -i  's/post_max_size = 8M/post_max_size = 64M/' /etc/php.ini
service php-fpm restart
rm -r /etc/nginx/nginx.conf
mv /home/nginx.conf /etc/nginx/
chown root:root /etc/nginx/nginx.conf
chown -R nginx:nginx /var/lib/php/session
chown -R nginx:nginx /usr/lib/systemd/system/nginx.service
nginx -t && nginx -s reload
systemctl restart nginx  
sed -i  's/user = apache/user = nginx/' /etc/php-fpm.d/www.conf
sed -i  's/group = apache/group = nginx/' /etc/php-fpm.d/www.conf
rm -f /usr/share/nginx/html/*
mv /home/clean_crl.php /usr/share/nginx/html/
mv /home/clean_crt.php /usr/share/nginx/html/
mv /home/getCert.php /usr/share/nginx/html/
mv /home/getCrl.php /usr/share/nginx/html/
mv /home/index.php /usr/share/nginx/html/
mv /home/upload.php /usr/share/nginx/html/
chmod -R 777 /usr/share/nginx/html/
chown -R nginx:nginx /usr/share/nginx/html/
nginx -t && nginx -s reload
sed -i  's/SELINUX=enforcing/SELINUX=disabled/' /etc/selinux/config
systemctl restart nginx
reboot
