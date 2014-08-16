
# Append our layer's path to override files			   
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://interfaces"
SRC_URI += "file://fstab"

do_install_append() {
    install -d ${D}${sysconfdir}
    install -d ${D}${sysconfdir}/network
    install -m 0600 ${WORKDIR}/interfaces ${D}${sysconfdir}/network/interfaces
    install -m 0600 ${WORKDIR}/fstab ${D}${sysconfdir}/fstab
}
