
# Add the CGI module
RDEPENDS_${PN} += " \
               lighttpd-module-cgi \
               lighttpd-module-rewrite \
               lighttpd-module-redirect \
               lighttpd-module-alias \
               lighttpd-module-auth \
               lighttpd-module-proxy \
               lighttpd-module-fastcgi \
			   "
# Append our layer's path to override files			   
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# Add the SSL certificate file
SRC_URI += "file://lighttpd.pem"
SRC_URI += "file://admin.user"

# Enable SSL
EXTRA_OECONF += " --with-openssl "

# Add cgi-bin directory
# Install SSL certificate
# Point to uploads directory
# Remove the default index.html
do_install_append() {
	install -d ${D}/www/pages/cgi-bin
    install -d ${D}${sysconfdir}
    install -d ${D}${sysconfdir}/lighttpd
    install -m 0600 ${WORKDIR}/lighttpd.pem ${D}${sysconfdir}/lighttpd/lighttpd.pem
    install -m 0666 ${WORKDIR}/admin.user ${D}${sysconfdir}/lighttpd/admin.user
    ln -s /home/root/uploads ${D}/www/uploads
    rm -f ${D}/www/pages/index.html
}

