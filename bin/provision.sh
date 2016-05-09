#!/bin/bash

# set our timezone
timedatectl set-timezone Europe/Berlin

# java
add-apt-repository ppa:webupd8team/java
apt-get update
echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
apt-get -y install oracle-java8-installer oracle-java8-set-default

# Install Docker
apt-get -y install apt-transport-https ca-certificates
apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D
echo "deb https://apt.dockerproject.org/repo ubuntu-trusty main" >/etc/apt/sources.list.d/docker.list
apt-get update
#apt-get -y install docker-engine=1.9.1-0~trusty
apt-get -y install docker-engine
usermod -aG docker vagrant
#echo \"DOCKER_OPTS=\\\"-r=true -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock \\${DOCKER_OPTS}\\\"\" >> /etc/default/docker
sudo service docker restart

# docker-compose
curl -L https://github.com/docker/compose/releases/download/1.7.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose

# make gradle wrapper easier to use
ln -sf /vagrant/bin/gradle /usr/local/bin/gradle

# Pre-Determine important IP Addresses automatically
echo "" >>/home/vagrant/.profile
echo "export IP=$(ifconfig eth1 | grep 'inet addr:' | cut -d: -f2 | awk '{ print $1}')" >>/home/vagrant/.profile
echo "export HOST_IP=`netstat -rn | grep "^0.0.0.0 " | cut -d " " -f10`" >>/home/vagrant/.profile
echo "export HOSTNAME=`hostname`" >>/home/vagrant/.profile
echo "export HANDSON_SERVER=192.168.33.100" >>/home/vagrant/.profile
echo "export SERVICE_IP=$(ifconfig eth1 | grep 'inet addr:' | cut -d: -f2 | awk '{ print $1}')" >>/home/vagrant/.profile


apt-get autoremove -y