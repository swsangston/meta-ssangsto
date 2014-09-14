DESCRIPTION = "A console-only image for Beaglebone or iMx.6"

#IMAGE_FEATURES += "splash"
IMAGE_FEATURES += "debug-tweaks"
#IMAGE_FEATURES += "ssh-server-openssh"
IMAGE_FEATURES += "ssh-server-dropbear"

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
    nodejs \
    vsftpd \
	lighttpd-module-auth \
	python-netserver \
    tzdata \
    gdb \
	gdbserver \
    strace \
	nano \
    my-apps \
    "

# iperf : not in standard Yocto: may be in meta-openembedded

inherit core-image

include useradd.inc

export IMAGE_BASENAME = "core-image-ssangsto"

