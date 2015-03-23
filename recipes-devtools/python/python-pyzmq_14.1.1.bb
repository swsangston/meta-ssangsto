DESCRIPTION = "ZeroMQ Python Bindings"
HOMEPAGE = "http://www.zeromq.org"
LICENSE = "LGPLv3+"
LIC_FILES_CHKSUM = "file://COPYING.LESSER;md5=a9339599829a9d0eb1b314ef43365ab5"
SRCNAME = "pyzmq"

SRC_URI = "https://github.com/zeromq/${SRCNAME}/archive/v${PV}.tar.gz"
SRC_URI[md5sum] = "e1f086be1cefea05e707e07ddece6161"
SRC_URI[sha256sum] = "7700811a342aa7beb3a08024280fcf6f4af062e4958b6467f6c3d9f20ad452fe"
SRCREV = "d2cdd44405cfb998f842946d5eb587725da24014"

DEPENDS = "python-native python-cython-native zeromq"

RDEPENDS_${PN} = "zeromq"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools

# Use the library that was built in the zeromq package
DISTUTILS_BUILD_ARGS += "--zmq=${STAGING_EXECPREFIXDIR}"

# Generate Cython backend and the setup.cfg file before compilation.
do_compile_prepend () {
# Should run host python from staging path, but it fails.
#   python ${S}/setup.py cython
    /usr/bin/python ${S}/setup.py cython
   
    echo "[bdist_egg]" > ${S}/setup.cfg
    echo "plat-name = linux-${TARGET_ARCH}" >> ${S}/setup.cfg
    echo "[global]" >> ${S}/setup.cfg
    echo "skip_check_zmq = True" >> ${S}/setup.cfg
}

# Ignore "QA Issue: non debug package contains .debug directory"
INSANE_SKIP_${PN} += "debug-files"
