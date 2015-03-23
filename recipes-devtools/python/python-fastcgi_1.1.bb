# ISCO: Custom recipe for python-fastcgi package
SUMMARY = "Python Fast CGI"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=898b70e243d30d7c1fe17fc905702670"

SRC_URI="https://pypi.python.org/packages/source/p/python-fastcgi/python-fastcgi-${PV}.tar.gz \
    file://setup-py-remove-local-paths.patch;patch=1"

SRC_URI[md5sum] = "9b00f6dd03c38c97f59eaf06cd0abeb5"
SRC_URI[sha256sum] = "3dff515aecbda371fe5bcb4e449109ce72120ce7bb1edd0e7c2138e75528fd12"

S = "${WORKDIR}/python-fastcgi-${PV}"

inherit setuptools

DEPENDS += "fastcgi"
RDEPENDS_${PN} = "fastcgi"
