
# look for files in this layer first
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Sdd any other modified /etc files from base-files recipe here
SRC_URI += "file://fstab"

# TODO: Move this to a dropbear layer?
SRC_URI += "file://dropbear"

do_install_append() {
    install -d ${D}${sysconfdir}/default
    install -m 0600 ${WORKDIR}/dropbear ${D}${sysconfdir}/default/dropbear
}
# end TODO

