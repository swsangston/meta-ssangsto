# Patched Glog to customize the log line format

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://isco-logformat.patch"
SRC_URI += "file://isco-timezone-update.patch"
