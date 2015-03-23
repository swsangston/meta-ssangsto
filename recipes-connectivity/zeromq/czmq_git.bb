DESCRIPTION = "C bindings for ZeroMQ"
HOMEPAGE = "http://www.zeromq.org"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"
DEPENDS = "zeromq"
# Rev 3.0.0 git tag
SRCREV = "9c24aea3defe84cd72d7e6ac1845b6215f3d7aad"
PR = "r0"

SRC_URI = "git://github.com/zeromq/czmq.git"

S = "${WORKDIR}/git"

inherit autotools

RDEPENDS_${PN} = "zeromq"
