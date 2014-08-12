DESCRIPTION = "A console-only image for Beaglebone or iMx.6"

#IMAGE_FEATURES += "splash"
IMAGE_FEATURES += "debug-tweaks"
#IMAGE_FEATURES += "ssh-server-openssh"
IMAGE_FEATURES += "ssh-server-dropbear"

IMAGE_FEATURES += "eclipse-debug"

IMAGE_INSTALL = "\
    packagegroup-core-boot \
    packagegroup-core-full-cmdline \
    wpa-supplicant \
    usbutils \
    linux-firmware \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    "

# Note: lighttpd is already included above

# QA issue when installing vsftp (doesn't occur with isco)
#ERROR: QA Issue: vsftpd: Files/directories were installed but not shipped
#  /run
#  /run/vsftpd
#  /run/vsftpd/empty
#ERROR: QA run found fatal errors. Please consider fixing them.
#ERROR: Function failed: do_package_qa

IMAGE_INSTALL += "\
    nodejs \
    gdb \
	gdbserver \
    strace \
    my-apps \
    kernel-modules \
    "
#    vsftpd \
#    service \
#


inherit core-image

include useradd.inc

export IMAGE_BASENAME = "core-image-ssangsto"

