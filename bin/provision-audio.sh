#!/bin/bash
apt-get update
apt-get -y install dkms

#https://wiki.ubuntu.com/Audio/UpgradingAlsa/DKMS
wget http://ppa.launchpad.net/ubuntu-audio-dev/alsa-daily/ubuntu/pool/main/o/oem-audio-hda-daily-dkms/oem-audio-hda-daily-dkms_0.201604180501~ubuntu14.04.1_all.deb
dpkg -i oem-audio-hda-daily-dkms_0.201604180501~ubuntu14.04.1_all.deb
rm oem-audio-hda-daily-dkms_0.201604180501~ubuntu14.04.1_all.deb
apt-get -y install python-dev ipython python-numpy python-matplotlib python-scipy cython alsa-utils paman
usermod -a -G audio vagrant

# Say command
#apt-get install gnustep-gui-runtime


# Reboot after audio install