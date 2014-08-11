DESCRIPTION = "An initramfs image for Beaglebone or iMx.6"

IMAGE_INSTALL = "\
    initramfs-live-boot \
    busybox \
    udev \
    base-files \
    base-passwd \
    init-ifupdown \
    e2fsprogs-e2fsck \ 
"
# e2fsprogs-mke2fs

# Do not pollute the initrd image with rootfs features
IMAGE_FEATURES = ""

IMAGE_FEATURES += "ssh-server-dropbear"

# Disable root password
IMAGE_FEATURES += "debug-tweaks"

export IMAGE_BASENAME = "initramfs-ssangsto"

# remove locale support
IMAGE_LINGUAS = ""

LICENSE = "MIT"

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"
inherit core-image

IMAGE_ROOTFS_SIZE = "8192"

BAD_RECOMMENDATIONS += "busybox-syslog"
BAD_RECOMMENDATIONS += "update-alternatives-opkg"


