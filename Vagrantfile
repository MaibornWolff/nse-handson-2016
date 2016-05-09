# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|

  config.vm.define "handson-server" do |host|

    host.vm.box = "ubuntu/trusty64"
    host.vm.hostname = "handson-server"
    host.vm.network "private_network", ip: "192.168.33.100"

    host.vm.provider "virtualbox" do |v|
      if ENV['HANDSON_SERVER']
        v.memory = 5000
      else
        v.memory = 3128
      end

      v.cpus = 2
    end

    # RabbitMQ Ports
    host.vm.network "forwarded_port", guest: 15672, host: 15672
    host.vm.network "forwarded_port", guest: 5672, host: 5672

    # Mongo Ports
    host.vm.network "forwarded_port", guest: 27017, host: 27017

    # Consul Ports
    host.vm.network "forwarded_port", guest: 8300, host: 8300
    host.vm.network "forwarded_port", guest: 8301, host: 8301
    host.vm.network "forwarded_port", guest: 8302, host: 8302
    host.vm.network "forwarded_port", guest: 8301, host: 8301, protocol: "udp"
    host.vm.network "forwarded_port", guest: 8302, host: 8302, protocol: "udp"
    host.vm.network "forwarded_port", guest: 8400, host: 8400
    host.vm.network "forwarded_port", guest: 8500, host: 8500
    host.vm.network "forwarded_port", guest: 53, host: 53, protocol: "udp"

    # Dashboard Loadbalancer Port
    host.vm.network "forwarded_port", guest: 8091, host: 8091
    host.vm.network "forwarded_port", guest: 8092, host: 8092
    host.vm.network "forwarded_port", guest: 8093, host: 8093
    host.vm.network "forwarded_port", guest: 8094, host: 8094
    host.vm.network "forwarded_port", guest: 8095, host: 8095

    # Ports for services started on server
    (8181..8189).each do |i|
      host.vm.network "forwarded_port", guest: i, host: i
    end

    # ELK Ports
    host.vm.network "forwarded_port", guest: 9200, host: 9200
    host.vm.network "forwarded_port", guest: 9300, host: 9300
    host.vm.network "forwarded_port", guest: 5140, host: 5140
    host.vm.network "forwarded_port", guest: 5601, host: 5601

    # Default Provision once
    host.vm.provision "shell", path: "bin/provision.sh"

    #dynamically set environment variables
    host.vm.provision "shell", inline: "echo \"source /home/vagrant/variables\" >>/home/vagrant/.profile"
    host.vm.provision "shell", run: "always", inline: "echo \"\" >/home/vagrant/variables"
    if ENV['HANDSON_SERVER']
      handsonserver = ENV['HANDSON_SERVER']
      host.vm.provision "shell", run: "always", inline: "echo \"export HANDSON_SERVER=#{handsonserver}\" >>/home/vagrant/variables"
      host.vm.provision "shell", run: "always", inline: "echo \"export SERVICE_IP=#{handsonserver}\" >>/home/vagrant/variables"
    end



    # Automatically Run Server Components on every boot
    host.vm.provision "shell", run: "always", inline: <<-SHELL
    docker images --quiet --filter=dangling=true | xargs --no-run-if-empty docker rmi
    cd /vagrant
    docker rm -f $(docker ps -aq)
    ./gradlew buildLoadBalancerDocker build
    su - vagrant -c "docker-compose --file /vagrant/docker-compose-server.yml up -d"
    SHELL

    if ENV['HANDSON_SERVER']
      host.vm.provision "shell", run: "always", inline: 'su - vagrant -c "docker-compose --file /vagrant/docker-compose-server-microservices.yml up -d"'
    end
  end


  number_of_instances = 3
  (1..number_of_instances).each do |instance_number|
    config.vm.define "handson#{instance_number}" do |host|
      host.vm.box = "ubuntu/trusty64"
      host.vm.network "private_network", ip: "192.168.33.11#{instance_number}"
      host.vm.hostname = "handson#{instance_number}"

      host.vm.provider "virtualbox" do |v|
        v.memory = 3500
        v.cpus = 2
      end

      # enable audio drivers on VM settings
      host.vm.provider :virtualbox do |vb|
        if RUBY_PLATFORM =~ /darwin/
          vb.customize ["modifyvm", :id, '--audio', 'coreaudio', '--audiocontroller', 'hda'] # choices: hda sb16 ac97
        elsif RUBY_PLATFORM =~ /mingw|mswin|bccwin|cygwin|emx/
          vb.customize ["modifyvm", :id, '--audio', 'dsound', '--audiocontroller', 'ac97']
        end
      end


      # Default Provision once
      host.vm.provision "shell", path: "bin/provision.sh"

      #dynamically set environment variables
      host.vm.provision "shell", inline: "echo \"source /home/vagrant/variables\" >>/home/vagrant/.profile"
      host.vm.provision "shell", run: "always", inline: "echo \"\" >/home/vagrant/variables"
      if ENV['HANDSON_SERVER']
        handsonserver = ENV['HANDSON_SERVER']
        host.vm.provision "shell", run: "always", inline: "echo \"export HANDSON_SERVER=#{handsonserver}\" >>/home/vagrant/variables"
      end


      #host.vm.provision "shell", path: "bin/provision-audio.sh"
      # reboot after audio provisioning
      #host.vm.provision :reload

      # Automatically Run Server Components on every boot
      host.vm.provision "shell", run: "always", inline: <<-SHELL
      docker rm -f $(docker ps -aq)
      su - vagrant -c "docker-compose --file /vagrant/docker-compose-node.yml up -d"
      SHELL

      # Simulate different DeviceIDs for BadgeReader on each node
      if instance_number == 1
        host.vm.provision "shell", inline: <<-SHELL
          echo "export DEVICE_ID=1" >>/home/vagrant/.profile
        SHELL
      elsif instance_number == 2
        host.vm.provision "shell", inline: <<-SHELL
          echo "export DEVICE_ID=2" >>/home/vagrant/.profile
        SHELL
      elsif instance_number == 3
        host.vm.provision "shell", inline: <<-SHELL
          echo "export DEVICE_ID=3" >>/home/vagrant/.profile
        SHELL
      end

      # To have the cluster running initially, start the apps on the nodes
      host.vm.provision "shell", run: "always", inline: <<-SHELL
        docker images --quiet --filter=dangling=true | xargs --no-run-if-empty docker rmi
        su - vagrant -c "cd /vagrant && ./gradlew build && docker-compose up -d"
      SHELL
    end
  end



  require 'etc'
  username = Etc.getlogin

  require 'socket'
  myIp = UDPSocket.open {|s| s.connect("8.8.8.8", 1); s.addr.last}

  puts "My IP: #{myIp}"
  if ENV['HANDSON_SERVER']
    puts "Overriding HANDSON_SERVER: #{ENV['HANDSON_SERVER']}"
  end

  config.vm.define "handson-dev", autostart: false do |host|

    host.vm.box = "ubuntu/trusty64"
    host.vm.network "private_network", ip: "192.168.33.90"
    host.vm.hostname = "handson-dev-#{username}"

    host.vm.provider "virtualbox" do |v|
      v.memory = 3128
      v.cpus = 2
    end

    # Consul Ports
     host.vm.network "forwarded_port", guest: 8300, host: 8300
     host.vm.network "forwarded_port", guest: 8301, host: 8301
     host.vm.network "forwarded_port", guest: 8302, host: 8302
     host.vm.network "forwarded_port", guest: 8301, host: 8301, protocol: "udp"
     host.vm.network "forwarded_port", guest: 8302, host: 8302, protocol: "udp"
     host.vm.network "forwarded_port", guest: 8400, host: 8400
     host.vm.network "forwarded_port", guest: 8500, host: 8500
     host.vm.network "forwarded_port", guest: 53, host: 53, protocol: "udp"


     #host.vm.network "forwarded_port", guest: 5601, host: 5601

    (8081..8089).each do |i|
      host.vm.network "forwarded_port", guest: i, host: i
    end

    # Mount Folder from which we call vagrant up. So every attendee has his own service in this same folder
    host.vm.synced_folder Dir.pwd, "/handson"


    # Default Provision once
    host.vm.provision "shell", path: "bin/provision.sh"

    #host.vm.provision "shell", path: "bin/provision-audio.sh"
    # Now reboot after audio provisioning
    #host.vm.provision :reload

    #dynamically set environment variables
    host.vm.provision "shell", inline: "echo \"source /home/vagrant/variables\" >>/home/vagrant/.profile"
    host.vm.provision "shell", run: "always", inline: "echo \"\" >/home/vagrant/variables"
    host.vm.provision "shell", run: "always", inline: "echo \"export SERVICE_IP=#{myIp}\" >>/home/vagrant/variables"
    handsonserver = ENV['HANDSON_SERVER']
    host.vm.provision "shell", run: "always", inline: "echo \"export HANDSON_SERVER=#{handsonserver}\" >>/home/vagrant/variables"


    # Automatically Run Server Components on every boot
    host.vm.provision "shell", run: "always", inline: <<-SHELL
      docker rm -f $(docker ps -aq)
      docker rmi $(docker images -q)
      su - vagrant -c "docker-compose --file /vagrant/docker-compose-node.yml up -d"
    SHELL
  end
end
