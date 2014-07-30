
# Add the CGI module
RDEPENDS_${PN} += " \
               lighttpd-module-cgi \
			   "

# Append our layer's path to override files			   
FILESEXTRAPATHS := "${THISDIR}/files:"

# Add cgi-bin directory
do_install_append() {
	install -d ${D}/www/pages/cgi-bin
}
