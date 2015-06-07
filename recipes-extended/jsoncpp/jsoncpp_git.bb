DESCRIPTION = "A C++ library for interacting with JSON."
HOMEPAGE = "https://github.com/open-source-parsers/jsoncpp"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c56ee55c03a55f8105b969d8270632ce"
#DEPENDS =

# Version 1.6.2 Git commit
SRCREV = "24682e37bf1425455b45e7db69dbdbcd30aec7dd"
SRC_URI = "git://github.com/open-source-parsers/jsoncpp.git"

# Must change PR whenever the SRCREV is changed
PR = "r2"

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
