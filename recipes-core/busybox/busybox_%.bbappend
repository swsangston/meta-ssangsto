
FILESEXTRAPATHS_prepend := "${THISDIR}/files:${THISDIR}/${PN}:"

SRC_URI += "file://crond.cfg"
SRC_URI += "file://ntpd.cfg"
SRC_URI += "file://rdev.cfg"
SRC_URI += "file://ifplugd.cfg"
SRC_URI += "file://resize.cfg"
SRC_URI += "file://chpasswd.cfg"
SRC_URI += "file://ntp.script"
SRC_URI += "file://ntpd-run"
SRC_URI += "file://crontab.root"

# Add cron package and install rc.d scripts
PACKAGES =+ "${PN}-cron"
INITSCRIPT_PACKAGES =+ "${PN}-cron"
FILES_${PN}-cron = "${sysconfdir}/init.d/cron"
INITSCRIPT_NAME_${PN}-cron = "cron"
INITSCRIPT_PARAMS_${PN}-cron = "defaults"

do_install_append() {
    install -d ${D}${sysconfdir}
    install -d ${D}${sysconfdir}/cron
    install -d ${D}${sysconfdir}/cron/crontabs
    install -m 0755 ${WORKDIR}/busybox-cron ${D}${sysconfdir}/init.d/cron
    rm -f ${D}${sysconfdir}/init.d/busybox-cron
    install -m 0644 ${WORKDIR}/crontab.root ${D}${sysconfdir}/cron/crontabs/root

    install -d ${D}${sbindir}
    install -m 0755 ${WORKDIR}/ntpd-run ${D}${sbindir}
    install -m 0755 ${WORKDIR}/ntp.script ${D}${sysconfdir}
}
