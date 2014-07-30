
# look for files in the layer first
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://crond.cfg"
SRC_URI += "file://ntpd.cfg"

