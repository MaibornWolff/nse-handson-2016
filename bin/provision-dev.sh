#!/bin/bash

apt-get update
apt-get install -y debconf-utils vim

#debconf-set-selections < /vagrant/bin/keyboard.conf
#dpkg-reconfigure keyboard-configuration -f noninteractive


mkdir -p /opt/idea
cd /opt
wget --no-check-certificate https://download.jetbrains.com/idea/ideaIU-2016.1.1.tar.gz
tar -xvf ideaIU*.tar.gz
mv idea-IU-*/* idea/

echo "alias idea='/opt/idea/bin/idea.sh'" >>/home/vagrant/.profile
echo "setxkbmap de" >>/home/vagrant/.profile


apt-get autoremove