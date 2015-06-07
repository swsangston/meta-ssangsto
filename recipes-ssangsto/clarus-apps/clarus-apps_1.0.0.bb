DESCRIPTION = "ISCO Clarus application and init script"
SECTION = "application"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Proprietary;md5=0557f9d92cf58f2ccdd50f62f8ac0b28"

PR="r0"

# Install rc.d links to start clarus '98th' at runlevel 2-5, and stop 1st at RL 6.
inherit update-rc.d
INITSCRIPT_NAME = "clarus"
INITSCRIPT_PARAMS = "defaults 98 1"

SRC_URI += "file://clarus"
SRC_URI += "file://clarus-fs"
SRC_URI += "file://clarus-boot"
SRC_URI += "file://set_mac_addrs"
SRC_URI += "file://bin.tgz"
SRC_URI += "file://lib.tgz"
SRC_URI += "file://scripts.tgz"
SRC_URI += "file://html.tgz"

# APPSDIR shell environment var points to application base directory
#FILESEXTRAPATHS_prepend := "${APPSDIR}/pkg:${THISDIR}/files"
FILESEXTRAPATHS_prepend := "${APPSDIR}/pkg"

USRLOCDIR = "/usr/local"
CGIBINDIR = "/www/pages/cgi-bin"
HTMLDIR = "/www/pages"
TESTDIR = "/www/pages/test"
RESETDIR = "/www/pages/reset"

# Task to clean up previously extracted tarballs from working directory
# in case apps dir contents have changed.
addtask do_preunpack after do_fetch before do_unpack
do_preunpack() {
	if [ -d "${WORKDIR}/bin" ]; then
        rm -rf ${WORKDIR}/bin
    fi
   	if [ -d "${WORKDIR}/lib" ]; then
        rm -rf ${WORKDIR}/lib
    fi
	if [ -d "${WORKDIR}/scripts" ]; then
        rm -rf ${WORKDIR}/scripts
    fi
	if [ -d "${WORKDIR}/html" ]; then
        rm -rf ${WORKDIR}/html
    fi
}

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/clarus ${D}${sysconfdir}/init.d
    
    install -d ${D}${sysconfdir}/rcS.d
    install -m 0755 ${WORKDIR}/clarus-boot ${D}${sysconfdir}/rcS.d/S90clarus-boot
    install -m 0755 ${WORKDIR}/clarus-fs ${D}${sysconfdir}/rcS.d/S02clarus-fs
    install -m 0755 ${WORKDIR}/set_mac_addrs ${D}${sysconfdir}/rcS.d/S89set_mac_addrs

    install -d ${D}${USRLOCDIR} ${D}${USRLOCDIR}/bin
    install -d ${D}${libdir}
    install -d ${D}/www ${D}${HTMLDIR} ${D}${CGIBINDIR} ${D}${TESTDIR} ${D}${RESETDIR}
    install -d ${D}${HTMLDIR}/_images ${D}${HTMLDIR}/_includes ${D}${HTMLDIR}/_library ${D}${HTMLDIR}/js ${D}${HTMLDIR}/js/images

    # Add Git SHA1 to /etc
    git rev-parse HEAD > ${D}${sysconfdir}/git-sha1

    # Install programs and libraries
	rm -f ${WORKDIR}/bin/.gitignore
	for f in ${WORKDIR}/bin/*; do
   		install -m 0755 ${f} ${D}${USRLOCDIR}/bin
	done

   	rm -f ${WORKDIR}/lib/.gitignore
	for f in ${WORKDIR}/lib/*; do
   		install -m 0755 ${f} ${D}${libdir}
	done

	# Scripts are in subdirs per type or application:
	# scripts/python, scripts/node, scripts/cgi, etc.
	for d in ${WORKDIR}/scripts/*; do
		if [ -d "${d}" ]; then
			cd $d
			for f in *; do
  				install -m 0755 ${d}/${f} ${D}${USRLOCDIR}/bin
		   	   	if [ "${d}" = "${WORKDIR}/scripts/cgi" ]; then
				    ln -s ${USRLOCDIR}/bin/${f} ${D}${CGIBINDIR}/${f}
	   			fi
			done
		fi
	done

	# HTML pages
	for f in ${WORKDIR}/html/*; do
		if [ -f ${f} ]; then
			install -m 0644 ${f} ${D}${HTMLDIR}
		fi
	done
	if [ -d ${WORKDIR}/html/_images ]; then
		for f in ${WORKDIR}/html/_images/*; do
			install -m 0644 ${f} ${D}${HTMLDIR}/_images
		done
	fi
	if [ -d ${WORKDIR}/html/_includes ]; then
		for f in ${WORKDIR}/html/_includes/*; do
 			install -m 0644 ${f} ${D}${HTMLDIR}/_includes
		done
	fi
	if [ -d ${WORKDIR}/html/_library ]; then
		for f in ${WORKDIR}/html/_library/*; do
   			install -m 0644 ${f} ${D}${HTMLDIR}/_library
		done
	fi
    if [ -d ${WORKDIR}/html/js ]; then
        for f in ${WORKDIR}/html/js/*; do
		if [ -f ${f} ]; then
                   install -m 0644 ${f} ${D}${HTMLDIR}/js
                fi
        done
    fi
    if [ -d ${WORKDIR}/html/js/images ]; then
        for f in ${WORKDIR}/html/js/images/*; do
            install -m 0644 ${f} ${D}${HTMLDIR}/js/images
        done
    fi
    if [ -d ${WORKDIR}/html/test ]; then
        for f in ${WORKDIR}/html/test/*; do
            install -m 0644 ${f} ${D}${HTMLDIR}/test
        done
    fi
    if [ -d ${WORKDIR}/html/reset ]; then
        for f in ${WORKDIR}/html/reset/*; do
            install -m 0644 ${f} ${D}${HTMLDIR}/reset
        done
    fi

}

# Add /usr/local/bin to packaging paths
FILES_${PN} += "${USRLOCDIR}/bin/* ${libdir}/* ${CGIBINDIR}/* ${HTMLDIR}/* ${HTMLDIR}/_images/* ${HTMLDIR}/_includes/* ${HTMLDIR}/_library/* ${HTMLDIR}/js/* ${HTMLDIR}/js/images/* ${HTMLDIR}/reset/* ${HTMLDIR}/test/*"
FILES_${PN}-dbg += "${USRLOCDIR}/bin/.debug/* ${libdir}/.debug/*"
PACKAGES = "${PN} ${PN}-dbg"

