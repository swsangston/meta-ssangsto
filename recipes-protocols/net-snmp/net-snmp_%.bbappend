
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://snmpd.conf"

# Remove unnecessary files that were installed from snmp-client package
do_install_append() {
	cd ${WORKDIR}/packages-split/net-snmp-client${bindir}
	for f in *; do
		if [ ! "${f}" = "snmptrap" ] && [ ! "${f}" = "snmpinform" ] ; then
			rm -f ${D}${bindir}/${f}
		fi
	done

	rm -f ${D}${bindir}/mib2c*

    rm -fr ${D}/usr/share/snmp/mib2c-data
    rm -fr ${D}/usr/share/snmp/snmpconf-data
    rm -f ${D}/usr/share/snmp/mib2c*
}
