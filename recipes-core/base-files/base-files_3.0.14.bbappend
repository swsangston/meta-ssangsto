# Recipe for files to be changed from base-files package or 
# to be added to /etc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fstab"
SRC_URI += "file://dropbear"

do_install_append() {
    install -d ${D}${sysconfdir}/default
    install -m 0600 ${WORKDIR}/dropbear ${D}${sysconfdir}/default/dropbear
}

