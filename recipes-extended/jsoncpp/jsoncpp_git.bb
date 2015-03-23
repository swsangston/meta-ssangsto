DESCRIPTION = "A C++ library for interacting with JSON."
HOMEPAGE = "https://github.com/open-source-parsers/jsoncpp"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c56ee55c03a55f8105b969d8270632ce"
#DEPENDS =

# Version 1.0.0 Git commit
SRCREV = "7165f6ac4c482e68475c9e1dac086f9e12fff0d0"
SRC_URI = "git://github.com/open-source-parsers/jsoncpp.git"

# Must change PR whenever the SRCREV is changed
PR = "r1"

S = "${WORKDIR}/git"

LDFLAGS += "-fPIC -shared"

do_compile() {
#    python ${D}/amalgamate.py
    /usr/bin/python ${S}/amalgamate.py
    
    cd ${S}/dist
    ${CXX} jsoncpp.cpp -I. -o libjsoncpp.so ${LDFLAGS} 
}

do_install() {
    install -d ${D}${includedir}
    install -d ${D}${includedir}/json
    install -d ${D}${libdir}
    
    install -m 0755 ${S}/dist/libjsoncpp.so ${D}${libdir}
    
    for f in ${S}/dist/json/*.h; do
        install -m 0644 ${f} ${D}${includedir}/json
    done
}

FILES_${PN} += "${libdir}/* ${includedir}/json/*"
PACKAGES = "${PN} ${PN}-dbg ${PN}-staticdev ${PN}-dev"
