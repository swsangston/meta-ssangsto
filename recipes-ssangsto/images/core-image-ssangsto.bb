DESCRIPTION = "A console-only image for Beaglebone or iMx.6"

#IMAGE_FEATURES += "splash"
IMAGE_FEATURES += "debug-tweaks"
IMAGE_FEATURES += "ssh-server-openssh"
#IMAGE_FEATURES += "ssh-server-dropbear"

#IMAGE_FEATURES += "eclipse-debug"

IMAGE_INSTALL = "\
    packagegroup-core-boot \
    packagegroup-core-full-cmdline \
    wpa-supplicant \
    kernel-modules  \
    "

# Required for rtlwifi but installs firmware for all devices (>50M). 
#    linux-firmware 

# Note: lighttpd is already included above

IMAGE_INSTALL += "\
    avahi \
    nodejs \
    vsftpd \
    openssh-sftp-server \
	lighttpd-module-auth \
	python-netserver \
    python-compression \
    tzdata \
    iputils \
    gdb \
	gdbserver \
    strace \
	nano \
	watchdog \
	glog \
    jsoncpp \
    zeromq \
    czmq \
    cppzmq-dev \
    python-json \
    python-pyzmq \
    python-pydoc \
    my-apps \
    "

# iperf : not in standard Yocto: may be in meta-openembedded
# net-snmp-server : not in standard Yocto
# busybox-cron : Unix cron is already installed (incompatible)
# python-modules : include all Poky Python modules

inherit core-image

include addusers.inc

export IMAGE_BASENAME = "core-image-ssangsto"

