
# Add the CGI module
RDEPENDS_${PN} += " \
               lighttpd-module-cgi \
			   "

# Append our layer's path to override files			   
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# Add the SSL certificate file
SRC_URI += "file://lighttpd.pem"

# Enable SSL
EXTRA_OECONF += " --with-openssl "

# Add cgi-bin directory
do_install_append() {
	install -d ${D}/www/pages/cgi-bin
    install -d ${D}${sysconfdir}
    install -d ${D}${sysconfdir}/lighttpd
    install -m 0600 ${WORKDIR}/lighttpd.pem ${D}${sysconfdir}/lighttpd/lighttpd.pem
}

