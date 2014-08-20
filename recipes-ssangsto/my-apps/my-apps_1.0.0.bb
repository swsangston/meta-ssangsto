DESCRIPTION = "App startup and install layer"
SECTION = "application"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Proprietary;md5=0557f9d92cf58f2ccdd50f62f8ac0b28"

PR="r0"

# Install rc.d links to start '98th' at runlevel 2-5, and stop 1st at RL 6.
inherit update-rc.d
INITSCRIPT_NAME = "myapps"
INITSCRIPT_PARAMS = "defaults 98 1"

SRC_URI += "file://myapps"
SRC_URI += "file://update-rootfs"

# The application's "make install" will copy the tar.gz file here.
# TODO: Change so that this recipe gets the files from apps/bin directory?
#SRC_URI += "file://apps-bin-1.0.tar.gz"

FILESEXTRAPATHS := "${THISDIR}/files:"

do_install() {
	install -d ${D}${bindir}
    install -d ${D}${sysconfdir}/init.d

	install -m 0755 ${WORKDIR}/myapps ${D}${sysconfdir}/init.d	
	install -m 0755 ${WORKDIR}/update-rootfs ${D}${bindir}

    # Install application files to /usr/bin
#    for f in ${WORKDIR}/bin/*;	do
#		install -m 0755 ${f} ${D}${bindir}
#	done
}


