
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# Customize watchdog.conf to enable hardware watchdog
SRC_URI += "file://watchdog.conf"

do_install_append() {
    install -m 0644 ${WORKDIR}/watchdog.conf ${D}${sysconfdir}/watchdog.conf
}

