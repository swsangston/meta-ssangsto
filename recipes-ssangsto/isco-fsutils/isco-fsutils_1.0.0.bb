DESCRIPTION = "ISCO BSP Filesystem Image Utilities"
SECTION = "BSP"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Proprietary;md5=0557f9d92cf58f2ccdd50f62f8ac0b28"
PR="r0"

SRC_URI += "file://uboot2emmc"
SRC_URI += "file://flashmmc"
SRC_URI += "file://mkmmcparts"
SRC_URI += "file://mkmmc2parts"
SRC_URI += "file://rootfs2parta"
SRC_URI += "file://update-rootfs"
SRC_URI += "file://update-kernel"
SRC_URI += "file://flash_all"

RDEPENDS_isco-fsutils := " \
    e2fsprogs-e2fsck \
    e2fsprogs-mke2fs \
    util-linux-sfdisk \
    dosfstools \
"

do_install() {
    install -d ${D}${sbindir}
    install -m 0700 ${WORKDIR}/uboot2emmc ${D}${sbindir}
    install -m 0744 ${WORKDIR}/flashmmc ${D}${sbindir}
    install -m 0744 ${WORKDIR}/mkmmcparts ${D}${sbindir}
    install -m 0744 ${WORKDIR}/mkmmc2parts ${D}${sbindir}
    install -m 0755 ${WORKDIR}/rootfs2parta ${D}${sbindir}
    install -m 0755 ${WORKDIR}/flash_all ${D}${sbindir}

	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/update-rootfs ${D}${bindir}
	install -m 0755 ${WORKDIR}/update-kernel ${D}${bindir}
}
