DESCRIPTION = "App startup and install layer"
SECTION = "application"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Proprietary;md5=0557f9d92cf58f2ccdd50f62f8ac0b28"

# TODO: Merge ISCO's latest

PR="r0"

# The application's "make install" copies the tar.gz file here.
# TODO: Change it so that this recipe gets the files from apps/bin directory?
SRC_URI = "file://apps-bin-1.0.tar.gz"
SRC_URI += "file://cprootimg"

FILESEXTRAPATHS := "${THISDIR}/files:"

do_install() {
	install -d ${D}${bindir}
	
	install -m 0755 ${WORKDIR}/cprootimg ${D}${bindir}

    	for f in ${WORKDIR}/bin/*;	do
		install -m 0755 ${f} ${D}${bindir}
	done
}


