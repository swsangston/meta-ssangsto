
FILESEXTRAPATHS := "${THISDIR}/files:"

# This seems to be the correct way to fix a QA error regarding /run directories.
# Clean up for a bad line in the recipe which installs a directory at /run
do_install_append() {
    if [ -d ${D}${localstatedir}/run/vsftpd/empty ]; then rmdir ${D}${localstatedir}/run/vsftpd/empty; fi
    if [ -d ${D}${localstatedir}/run/vsftpd ]; then rmdir ${D}${localstatedir}/run/vsftpd; fi
    if [ -d ${D}${localstatedir}/run ]; then rmdir ${D}${localstatedir}/run; fi
}

FILES_${PN} += "${localstatedir}"

