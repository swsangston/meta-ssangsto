DESCRIPTION = "C++ bindings for ZeroMQ"
HOMEPAGE = "http://www.zeromq.org"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=db174eaf7b55a34a7c89551197f66e94"
DEPENDS = "zeromq"

# GitHub commit for rev 4.0.0 (8/6/2014)
SRCREV = "ee47ae4cddc304741526c9bb2035f98c3274e0ec"

SRC_URI = "git://github.com/zeromq/cppzmq.git"

# Must change PR whenever the SRCREV is changed
PR = "r0"

S = "${WORKDIR}/git"

do_install () {
        install -d ${D}/usr/include
        install -m 0755 ${S}/zmq.hpp ${D}/usr/include/
}

PACKAGES = "${PN}-dev"

RDEPENDS_${PN}-dev = "zeromq-dev"
