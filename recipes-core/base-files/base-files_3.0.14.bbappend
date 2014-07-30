
# look for files in this layer first
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Todo: add any other modified /etc files from base-files recipe here
#SRC_URI += "file://inittab"
SRC_URI += "file://fstab"

