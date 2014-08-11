
FILESEXTRAPATHS := "${THISDIR}/files:"

#do_package_qa() {
#    :
#}

#do_install_append() {
#    if [ -d ${D}${localstatedir}/run ]; then rmdir ${D}${localstatedir}/run; fi
#}

FILES_${PN} += "${localstatedir}"

