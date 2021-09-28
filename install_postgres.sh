#!/bin/sh
yum -y update
yum -y install epel-release
yum -y install wget bzip2 traceroute net-tools nano bind-utils cifs-utils telnet htop atop iftop lsof git rsync policycoreutils-python-utils tar zip unzip
hostnamectl set-hostname s1c
echo "172.16.2.102 s1c" >> /etc/hosts
rpm -i http://repo.postgrespro.ru/pgpro-12/keys/centos.rpm
yum makecache
yum -y install postgrespro-std-12
rm -rf /var/lib/pgpro/std-12/data
/opt/pgpro/std-12/bin/pg-setup initdb --tune=1c --locale=ru_RU.UTF-8
sleep 1m 
systemctl enable --now postgrespro-std-12
systemctl restart postgrespro-std-12
echo "host all all 127.0.0.1/32 md5" >> /var/lib/pgpro/std-12/data/pg_hba.conf
echo "host all all 172.16.2.0/24 md5" >> /var/lib/pgpro/std-12/data/pg_hba.conf
systemctl status postgrespro-std-12
firewall-cmd --permanent --zone=public --add-port=5432/tcp
systemctl restart firewalld
echo "username=guest" >> /root/.smbclient
echo "password=dgfpbqz382" >> /root/.smbclient
echo "//172.16.2.44/BackUP /net cifs guest, file_mode=0777,dir_mode=0777,credentials=/root/.smbclient 0 0" >> /etc/fstab
