# Recipe for files to be changed from base-files package or 
# to be added to /etc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fstab"
SRC_URI += "file://dropbear"
SRC_URI += "file://ssh"

do_install_append() {
    install -d ${D}${sysconfdir}/default
    install -m 0600 ${WORKDIR}/dropbear ${D}${sysconfdir}/default/dropbear
    install -m 0644 ${WORKDIR}/profile ${D}${sysconfdir}/profile
    install -m 0600 ${WORKDIR}/ssh ${D}${sysconfdir}/default/ssh
}

